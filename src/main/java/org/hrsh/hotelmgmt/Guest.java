package org.hrsh.hotelmgmt;

import java.util.List;

public class Guest extends User {
    private int totalRoomsCheckedIn;

    public RoomBooking bookRoom(List<RoomBooking> roomBookings) {
        return new RoomBooking();
    }

    public boolean cancelBooking(String bookingId) {
        return true;
    }
}
