package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
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
        return players.add(player);
    }
}
