package org.hrsh.movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Reservation {
    private String id;
    private User user;
    private Show show;
    private List<Seat> seats;
    private LocalDateTime reservedAt;
    public static final int RESERVATION_TIMEOUT_MINUTES = 15;

    public Reservation(User user, Show show, List<Seat> seats) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.reservedAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(reservedAt.plusMinutes(RESERVATION_TIMEOUT_MINUTES));
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Show getShow() {
        return show;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public LocalDateTime getReservedAt() {
        return reservedAt;
    }
}
