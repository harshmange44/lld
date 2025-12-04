package org.hrsh.hotelmgmt;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class HotelManagementSystem {
    /**
     * Main orchestrator for hotel management system
     * Handles bookings, room management, check-in/check-out
     */

    private final Map<String, Hotel> hotels;
    private final Map<String, RoomBooking> bookings;
    private final Map<String, Guest> guests;
    private final Map<String, Receptionist> receptionists;
    private final Map<String, Admin> admins;
    private final BookingService bookingService;
    private final RoomService roomService;
    private final NotificationService notificationService;

    public HotelManagementSystem() {
        this.hotels = new ConcurrentHashMap<>();
        this.bookings = new ConcurrentHashMap<>();
        this.guests = new ConcurrentHashMap<>();
        this.receptionists = new ConcurrentHashMap<>();
        this.admins = new ConcurrentHashMap<>();
        this.bookingService = new BookingService();
        this.roomService = new RoomService();
        this.notificationService = new NotificationService();
    }

    // Hotel Management
    public void addHotel(Hotel hotel) {
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel cannot be null");
        }
        hotels.put(hotel.getHotelId(), hotel);
    }

    public Hotel getHotel(String hotelId) {
        return hotels.get(hotelId);
    }

    // Room Management
    public void addRoom(String hotelId, Room room) {
        Hotel hotel = hotels.get(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel not found");
        }
        roomService.addRoom(hotel, room);
    }

    public List<Room> searchAvailableRooms(String hotelId, LocalDate checkIn, LocalDate checkOut, RoomType roomType) {
        Hotel hotel = hotels.get(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel not found");
        }
        return roomService.searchAvailableRooms(hotel, checkIn, checkOut, roomType);
    }

    // Booking Management
    public BookingInfo bookRoom(String hotelId, Guest guest, List<Room> rooms, LocalDate checkIn, LocalDate checkOut) {
        if (guest == null || rooms == null || rooms.isEmpty() || checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Invalid booking parameters");
        }
        if (checkIn.isAfter(checkOut) || checkIn.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid check-in/check-out dates");
        }

        Hotel hotel = hotels.get(hotelId);
        if (hotel == null) {
            throw new IllegalArgumentException("Hotel not found");
        }

        // Check room availability and reserve rooms
        for (Room room : rooms) {
            if (!roomService.isRoomAvailable(room, checkIn, checkOut)) {
                throw new IllegalStateException("Room " + room.getRoomNumber() + " is not available");
            }
            // Reserve room in RoomService
            roomService.reserveRoom(room, checkIn, checkOut);
        }

        return bookingService.createBooking(hotel, guest, rooms, checkIn, checkOut, bookings, notificationService);
    }

    public boolean cancelBooking(String bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking ID cannot be null");
        }
        
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null) {
            return false;
        }
        
        boolean cancelled = bookingService.cancelBooking(bookingId, bookings, notificationService);
        
        if (cancelled) {
            // Release rooms from RoomService
            for (Room room : booking.getRooms()) {
                roomService.releaseRoom(room, booking.getCheckInDate(), booking.getCheckOutDate());
            }
        }
        
        return cancelled;
    }

    public RoomBooking getBooking(String bookingId) {
        return bookings.get(bookingId);
    }

    public List<RoomBooking> getGuestBookings(String guestId) {
        return bookings.values().stream()
                .filter(booking -> booking.getGuests().stream()
                        .anyMatch(guest -> guest.getId().equals(guestId)))
                .collect(Collectors.toList());
    }

    // Check-in/Check-out
    public boolean checkIn(String bookingId, Receptionist receptionist) {
        if (bookingId == null || receptionist == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        return bookingService.checkIn(bookingId, receptionist, bookings, roomService, notificationService);
    }

    public boolean checkOut(String bookingId, Receptionist receptionist) {
        if (bookingId == null || receptionist == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        
        RoomBooking booking = bookings.get(bookingId);
        if (booking == null) {
            return false;
        }
        
        boolean checkedOut = bookingService.checkOut(bookingId, receptionist, bookings, roomService, notificationService);
        
        if (checkedOut) {
            // Release rooms from RoomService
            for (Room room : booking.getRooms()) {
                roomService.releaseRoom(room, booking.getCheckInDate(), booking.getCheckOutDate());
            }
        }
        
        return checkedOut;
    }

    // Guest Management
    public void registerGuest(Guest guest) {
        if (guest == null || guest.getId() == null) {
            throw new IllegalArgumentException("Guest cannot be null");
        }
        guests.put(guest.getId(), guest);
    }

    public Guest getGuest(String guestId) {
        return guests.get(guestId);
    }

    // Staff Management
    public void registerReceptionist(Receptionist receptionist) {
        if (receptionist == null || receptionist.getId() == null) {
            throw new IllegalArgumentException("Receptionist cannot be null");
        }
        receptionists.put(receptionist.getId(), receptionist);
    }

    public void registerAdmin(Admin admin) {
        if (admin == null || admin.getId() == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }
        admins.put(admin.getId(), admin);
    }
}

