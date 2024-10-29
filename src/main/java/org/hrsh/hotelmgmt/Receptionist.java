package org.hrsh.hotelmgmt;

import java.util.List;

public class Receptionist extends User {
    private int totalRoomsCheckedIn;

    public BookingInfo bookRoom(List<RoomBooking> roomBookings) {
        BookingInfo bookingInfo = new BookingInfo(
                "B1",
                "T1",
                new Invoice(0, 0, 0, PaymentMethod.CREDIT_CARD),
                PaymentStatus.PENDING);
        return bookingInfo;
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }

    public boolean checkIn(RoomBooking roomBooking, Guest guest) {
        return true;
    }

    public boolean checkOut(RoomBooking roomBooking, Guest guest) {
        return true;
    }
}
