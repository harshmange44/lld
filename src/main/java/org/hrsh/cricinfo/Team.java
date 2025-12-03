package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Team {
    private String id;
    private String name;
    private Country country;
    private List<Player> players;

    public Team(String name, Country country) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.country = country;
        this.players = new ArrayList<>();
    }

    public boolean addPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        return players.add(player);
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players); // Return defensive copy
    }

    public void setPlayers(List<Player> players) {
        this.players = players != null ? new ArrayList<>(players) : new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
