package org.hrsh.hotelmgmt;

import java.util.Date;
import java.util.List;

public class RoomBooking {
    private String bookingId;
    private List<Room> rooms;
    private List<Guest> guests;
    private double totalPrice;
    private Date bookingDate;
    private int durationInDays;
    private BookingStatus bookingStatus;
    private List<Notification> notifications;
}
