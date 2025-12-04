package org.hrsh.hotelmgmt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomBooking {
    private String bookingId;
    private List<Room> rooms;
    private List<Guest> guests;
    private double totalPrice;
    private LocalDate bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int durationInDays;
    private BookingStatus bookingStatus;
    private List<Notification> notifications;
    private List<RoomKey> roomKeys;
    private List<RoomCharge> roomCharges;
    private Invoice invoice;

    public RoomBooking(String bookingId, List<Room> rooms, List<Guest> guests, 
                      double totalPrice, LocalDate checkInDate, LocalDate checkOutDate,
                      int durationInDays, BookingStatus bookingStatus) {
        this.bookingId = bookingId;
        this.rooms = rooms != null ? new CopyOnWriteArrayList<>(rooms) : new CopyOnWriteArrayList<>();
        this.guests = guests != null ? new CopyOnWriteArrayList<>(guests) : new CopyOnWriteArrayList<>();
        this.totalPrice = totalPrice;
        this.bookingDate = LocalDate.now();
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.durationInDays = durationInDays;
        this.bookingStatus = bookingStatus;
        this.notifications = new CopyOnWriteArrayList<>();
        this.roomKeys = new CopyOnWriteArrayList<>();
        this.roomCharges = new CopyOnWriteArrayList<>();
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms); // Return defensive copy
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms != null ? new CopyOnWriteArrayList<>(rooms) : new CopyOnWriteArrayList<>();
    }

    public List<Guest> getGuests() {
        return new ArrayList<>(guests); // Return defensive copy
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests != null ? new CopyOnWriteArrayList<>(guests) : new CopyOnWriteArrayList<>();
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public List<Notification> getNotifications() {
        return new ArrayList<>(notifications); // Return defensive copy
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications != null ? new CopyOnWriteArrayList<>(notifications) : new CopyOnWriteArrayList<>();
    }

    public List<RoomKey> getRoomKeys() {
        return new ArrayList<>(roomKeys); // Return defensive copy
    }

    public void setRoomKeys(List<RoomKey> roomKeys) {
        this.roomKeys = roomKeys != null ? new CopyOnWriteArrayList<>(roomKeys) : new CopyOnWriteArrayList<>();
    }

    public List<RoomCharge> getRoomCharges() {
        return new ArrayList<>(roomCharges); // Return defensive copy
    }

    public void setRoomCharges(List<RoomCharge> roomCharges) {
        this.roomCharges = roomCharges != null ? new CopyOnWriteArrayList<>(roomCharges) : new CopyOnWriteArrayList<>();
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
