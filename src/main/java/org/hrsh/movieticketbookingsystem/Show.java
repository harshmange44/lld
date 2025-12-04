package org.hrsh.movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Show {
    private String id;
    private Movie movie;
    private Theatre theatre;
    private LocalDateTime showStartTime;
    private LocalDateTime endTime;
    private List<Seat> seats; // Thread-safe list

    public Show(String id, Movie movie, Theatre theatre, LocalDateTime showStartTime, LocalDateTime endTime, List<Seat> seats) {
        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.showStartTime = showStartTime;
        this.endTime = endTime;
        this.seats = seats != null ? new CopyOnWriteArrayList<>(seats) : new CopyOnWriteArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theatre getTheatre() {
        return theatre;
    }

    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }

    public LocalDateTime getShowStartTime() {
        return showStartTime;
    }

    public void setShowStartTime(LocalDateTime showStartTime) {
        this.showStartTime = showStartTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Seat> getSeats() {
        return seats; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats != null ? new CopyOnWriteArrayList<>(seats) : new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
