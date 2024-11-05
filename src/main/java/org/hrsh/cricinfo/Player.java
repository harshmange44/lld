package org.hrsh.cricinfo;

import java.util.List;
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
}
