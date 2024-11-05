package org.hrsh.movieticketbookingsystem;

import java.util.ArrayList;
import java.util.List;

public class MovieTicketBookingSystem {
    /*
      1. Multiple Shows
      2. Multiple Movies
      3. Multiple Theatres
      4. Book Tickets
      5. Reserve Seats before Booking for 15 minutes
     */

    private final List<Movie> movies;
    private final List<Show> shows;
    private final List<Theatre> theatres;

    public MovieTicketBookingSystem() {
        this.movies = new ArrayList<>();
        this.shows = new ArrayList<>();
        this.theatres = new ArrayList<>();
    }

    public void selectSeats(List<Seat> seats) {
        if (areSeatsAvailable(seats)) {
            markSeatsAsReserved(seats);
        }
    }

    public synchronized void markSeatsAsReserved(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.RESERVED);
        }
    }

    public synchronized Booking bookTickets(Show show, List<Seat> seats) {
        if (areSeatsReserved(seats)) {
            markSeatsAsBooked(seats);
            double totalPrice = calcTotalPrice(seats);
            return new Booking(new User(), show, seats, totalPrice, BookingStatus.PENDING);
        }
        return null;
    }

    public synchronized boolean confirmBooking(Booking booking) {
        if (booking.getBookingStatus() == BookingStatus.PENDING) {
            booking.setBookingStatus(BookingStatus.SUCCESSFUL);
            return true;
        }
        return false;
    }

    public synchronized boolean cancelBooking(Booking booking) {
        if (booking.getBookingStatus() == BookingStatus.SUCCESSFUL) {
            booking.setBookingStatus(BookingStatus.CANCELLED);
            markSeatsAsAvailable(booking.getSelectedSeats());
            return true;
        }
        return false;
    }

    private void markSeatsAsAvailable(List<Seat> selectedSeats) {
        for (Seat seat : selectedSeats) {
            seat.setSeatStatus(SeatStatus.AVAILABLE);
        }
    }

    private double calcTotalPrice(List<Seat> seats) {
        return seats.stream().mapToDouble(Seat::getPrice).sum();
    }

    private boolean areSeatsAvailable(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat != null && seat.getSeatStatus() != SeatStatus.AVAILABLE) return false;
        }
        return true;
    }

    private boolean areSeatsReserved(List<Seat> seats) {
        for (Seat seat : seats) {
            if (seat != null && seat.getSeatStatus() != SeatStatus.RESERVED) return false;
        }
        return true;
    }

    private void markSeatsAsBooked(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.setSeatStatus(SeatStatus.OCCUPIED);
        }
    }
}
