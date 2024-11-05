package org.hrsh.airlinemgmt;

import java.util.Map;

public class Booking {
    private String pnr;
    private Flight flight;
    private Map<Seat, User> seatUserMap;
    private double totalAmount;
    private BookingStatus bookingStatus;

    public Booking(String pnr, Flight flight, Map<Seat, User> seatUserMap, double totalAmount, BookingStatus bookingStatus) {
        this.pnr = pnr;
        this.flight = flight;
        this.seatUserMap = seatUserMap;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    public Booking updateBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
        return this;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
}
