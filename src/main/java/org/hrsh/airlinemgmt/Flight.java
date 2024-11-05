package org.hrsh.airlinemgmt;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight {
    private String id;
    private String flightNumber;
    private Location srcLocation;
    private Location destLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Seat> seats;
    private Pilot pilot;

    public Flight(String flightNumber, Location srcLocation, Location destLocation, LocalDateTime startTime, LocalDateTime endTime) {
        this.flightNumber = flightNumber;
        this.srcLocation = srcLocation;
        this.destLocation = destLocation;
        this.startTime = startTime;
        this.endTime = endTime;
        this.seats = new ArrayList<>();
    }

    public boolean initSeats(int noOfEconomySeats, int premiumSeats, int businessSeats) {
        int seatNum = 1;

        for (int i = 0; i < noOfEconomySeats; i++) {
            seats.add(new Seat(String.valueOf(seatNum++), SeatType.ECONOMY, Constants.DEFAULT_ECONOMY_PRICE));
        }
        for (int i = 0; i < premiumSeats; i++) {
            seats.add(new Seat(String.valueOf(seatNum++), SeatType.PREMIUM_ECONOMY, Constants.DEFAULT_PREMIUM_ECONOMY_PRICE));
        }
        for (int i = 0; i < businessSeats; i++) {
            seats.add(new Seat(String.valueOf(seatNum++), SeatType.BUSINESS, Constants.DEFAULT_BUSINESS_PRICE));
        }
        return true;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Location getSrcLocation() {
        return srcLocation;
    }

    public void setSrcLocation(Location srcLocation) {
        this.srcLocation = srcLocation;
    }

    public Location getDestLocation() {
        return destLocation;
    }

    public void setDestLocation(Location destLocation) {
        this.destLocation = destLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }
}
