package org.hrsh.movieticketbookingsystem;

import java.util.List;
import java.util.UUID;

public class Booking {
    private String id;
    private User user;
    private Show show;
    private List<Seat> selectedSeats;
    private double totalPrice;
    private BookingStatus bookingStatus;

    public Booking(User user, Show show, List<Seat> selectedSeats, double totalPrice, BookingStatus bookingStatus) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.show = show;
        this.selectedSeats = selectedSeats;
        this.totalPrice = totalPrice;
        this.bookingStatus = bookingStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
