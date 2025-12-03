package org.hrsh.cricinfo;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Player {
    private String id;
    private String name;
    private String jerseyNumber;
    private List<PlayerRole> playerRoles;

    public Player(String name, String jerseyNumber, List<PlayerRole> playerRoles) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.jerseyNumber = jerseyNumber;
        this.playerRoles = playerRoles;
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

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public List<PlayerRole> getPlayerRoles() {
        return playerRoles;
    }

    public void setPlayerRoles(List<PlayerRole> playerRoles) {
        this.playerRoles = playerRoles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
