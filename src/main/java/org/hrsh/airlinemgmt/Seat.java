package org.hrsh.airlinemgmt;

public class Seat {
    private String seatNumber;
    private SeatType seatType;
    private SeatStatus seatStatus;
    private double price;

    public Seat(String seatNumber, SeatType seatType, double price) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatStatus = SeatStatus.AVAILABLE;
        this.price = price;
    }
}
