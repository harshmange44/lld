package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Inning {
    private String id;
    private String battingTeamId;
    private String bowlingTeamId;
    private List<Over> overs;

    public Inning(String battingTeamId, String bowlingTeamId) {
        this.id = UUID.randomUUID().toString();
        this.battingTeamId = battingTeamId;
        this.bowlingTeamId = bowlingTeamId;
        this.overs = new ArrayList<>();
    }

    public boolean addOver(Over over) {
        return overs.add(over);
    }

    public String getId() {
        return id;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public List<Over> getOvers() {
        return overs;
    }
}
