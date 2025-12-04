package org.hrsh.movieticketbookingsystem;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Theatre {
    private String id;
    private String name;
    private Location location;
    private List<Show> shows;

    public Theatre(String id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.shows = new CopyOnWriteArrayList<>(); // Thread-safe
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Show> getShows() {
        return shows; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setShows(List<Show> shows) {
        this.shows = shows != null ? new CopyOnWriteArrayList<>(shows) : new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theatre theatre = (Theatre) o;
        return Objects.equals(id, theatre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
