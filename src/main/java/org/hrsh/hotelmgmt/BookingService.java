package org.hrsh.hotelmgmt;

import java.time.LocalDate;
import java.util.*;

public class BookingService {
    private static final double TAX_RATE = 0.18; // 18% tax
    private static final double DISCOUNT_RATE = 0.1; // 10% discount for now

    public BookingInfo createBooking(Hotel hotel, Guest guest, List<Room> rooms, 
                                     LocalDate checkIn, LocalDate checkOut,
                                     Map<String, RoomBooking> bookings,
                                     NotificationService notificationService) {
        // Calculate total price
        int durationInDays = (int) java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut);
        double baseAmount = rooms.stream()
                .mapToDouble(room -> room.getRoomPrice() * durationInDays)
                .sum();

        double discount = baseAmount * DISCOUNT_RATE;
        double taxAmount = (baseAmount - discount) * TAX_RATE;
        double totalAmount = baseAmount - discount + taxAmount;

        // Create invoice
        Invoice invoice = new Invoice(baseAmount, taxAmount, discount, PaymentMethod.CREDIT_CARD);

        // Create booking
        String bookingId = UUID.randomUUID().toString();
        RoomBooking booking = new RoomBooking(
                bookingId,
                new ArrayList<>(rooms),
                Arrays.asList(guest),
                totalAmount,
                checkIn,
                checkOut,
                durationInDays,
                BookingStatus.PENDING
        );

        // Mark rooms as reserved and update booking map
        for (Room room : rooms) {
            room.setRoomStatus(RoomStatus.RESERVED);
        }

        // Store booking
        bookings.put(bookingId, booking);
        
        // Set invoice in booking
        booking.setInvoice(invoice);

        // Create booking info
        BookingInfo bookingInfo = new BookingInfo(
                bookingId,
                UUID.randomUUID().toString(),
                invoice,
                PaymentStatus.PENDING
        );

        // Send notification
        notificationService.sendNotification(guest, NotificationType.CHECK_IN, 
                "Booking confirmed. Booking ID: " + bookingId);

        return bookingInfo;
    }

    public boolean cancelBooking(String bookingId, Map<String, RoomBooking> bookings,
                                 NotificationService notificationService) {
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null) {
            return false;
        }

        if (booking.getBookingStatus() == BookingStatus.CHECKED_IN || 
            booking.getBookingStatus() == BookingStatus.CHECKED_OUT) {
            return false; // Cannot cancel after check-in
        }

        // Mark rooms as available and release from RoomService
        for (Room room : booking.getRooms()) {
            room.setRoomStatus(RoomStatus.AVAILABLE);
            // Release room booking
            // Note: RoomService.releaseRoom should be called from HotelManagementSystem
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        
        // Send notification
        for (Guest guest : booking.getGuests()) {
            notificationService.sendNotification(guest, NotificationType.OTHER,
                    "Booking cancelled. Booking ID: " + bookingId);
        }

        return true;
    }

    public boolean checkIn(String bookingId, Receptionist receptionist,
                          Map<String, RoomBooking> bookings,
                          RoomService roomService,
                          NotificationService notificationService) {
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }

        if (booking.getBookingStatus() != BookingStatus.SUCCESSFUL &&
            booking.getBookingStatus() != BookingStatus.PENDING) {
            return false;
        }

        LocalDate today = LocalDate.now();
        if (!booking.getCheckInDate().equals(today) && !booking.getCheckInDate().isBefore(today)) {
            throw new IllegalStateException("Check-in date is not today");
        }

        // Update room status
        for (Room room : booking.getRooms()) {
            room.setRoomStatus(RoomStatus.OCCUPIED);
            // Issue room key
            roomService.issueRoomKey(room, booking);
        }

        booking.setBookingStatus(BookingStatus.CHECKED_IN);
        receptionist.incrementCheckedInCount();

        // Send notification
        for (Guest guest : booking.getGuests()) {
            notificationService.sendNotification(guest, NotificationType.CHECK_IN,
                    "Check-in successful. Booking ID: " + bookingId);
        }

        return true;
    }

    public boolean checkOut(String bookingId, Receptionist receptionist,
                           Map<String, RoomBooking> bookings,
                           RoomService roomService,
                           NotificationService notificationService) {
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null) {
            throw new IllegalArgumentException("Booking not found");
        }

        if (booking.getBookingStatus() != BookingStatus.CHECKED_IN) {
            return false;
        }

        // Update room status
        for (Room room : booking.getRooms()) {
            room.setRoomStatus(RoomStatus.AVAILABLE);
            // Collect room keys
            roomService.collectRoomKeys(room, booking);
            // Schedule housekeeping
            roomService.scheduleHouseKeeping(room);
            // Release room booking from RoomService
            // Note: This should be called from HotelManagementSystem after checkout
        }

        booking.setBookingStatus(BookingStatus.CHECKED_OUT);

        // Calculate final invoice with any additional charges
        double finalAmount = booking.getTotalPrice();
        // Add any room service charges
        for (RoomCharge charge : booking.getRoomCharges()) {
            if (charge instanceof RoomServiceCharge && ((RoomServiceCharge) charge).isChargeable()) {
                finalAmount += charge.getAmount();
            }
        }

        booking.getInvoice().setBaseAmount(finalAmount);

        // Send notification
        for (Guest guest : booking.getGuests()) {
            notificationService.sendNotification(guest, NotificationType.CHECK_OUT,
                    "Check-out successful. Final amount: " + finalAmount);
        }

        return true;
    }
}

