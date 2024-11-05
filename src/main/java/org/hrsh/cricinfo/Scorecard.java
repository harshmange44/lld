package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Scorecard {
    private String id;
    private Match match;
    private List<Inning> innings;
    private Map<String, Score> teamScoreMap;

    public Scorecard(Match match) {
        this.id = UUID.randomUUID().toString();
        this.match = match;
        this.innings = new ArrayList<>();
        this.teamScoreMap = new ConcurrentHashMap<>();
    }

    public boolean addInning(Inning inning) {
        return innings.add(inning);
    }

    public void updateScore(String teamId, Score score) {
        teamScoreMap.put(teamId, score);
    }
}
