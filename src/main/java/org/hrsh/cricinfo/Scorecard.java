package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Scorecard {
    private String id;
    private Match match;
    private List<Inning> innings;
    private Map<String, Score> teamScoreMap;

    public Scorecard(Match match) {
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        this.id = UUID.randomUUID().toString();
        this.match = match;
        this.innings = new CopyOnWriteArrayList<>(); // Thread-safe list
        this.teamScoreMap = new ConcurrentHashMap<>();
    }

    public synchronized boolean addInning(Inning inning) {
        if (inning == null) {
            throw new IllegalArgumentException("Inning cannot be null");
        }
        return innings.add(inning);
    }

    public synchronized void updateScore(String teamId, Score score) {
        if (teamId == null || score == null) {
            throw new IllegalArgumentException("Team ID and Score cannot be null");
        }
        teamScoreMap.put(teamId, score);
    }

    /**
     * Calculate total runs from all balls in all innings for a team
     */
    public int calculateTotalRuns(String teamId) {
        int totalRuns = 0;
        for (Inning inning : innings) {
            if (inning.getBattingTeamId().equals(teamId)) {
                totalRuns += inning.calculateTotalRuns();
            }
        }
        return totalRuns;
    }

    /**
     * Calculate total wickets from all innings for a team
     */
    public int calculateTotalWickets(String teamId) {
        int totalWickets = 0;
        for (Inning inning : innings) {
            if (inning.getBattingTeamId().equals(teamId)) {
                totalWickets += inning.calculateTotalWickets();
            }
        }
        return totalWickets;
    }

    /**
     * Get current score for a team (from scorecard or calculate from balls)
     */
    public Score getCurrentScore(String teamId) {
        Score score = teamScoreMap.get(teamId);
        if (score == null) {
            // Calculate from balls if not manually updated
            int runs = calculateTotalRuns(teamId);
            int wickets = calculateTotalWickets(teamId);
            score = new Score(runs, wickets);
            teamScoreMap.put(teamId, score);
        }
        return score;
    }

    public String getId() {
        return id;
    }

    public Match getMatch() {
        return match;
    }

    public List<Inning> getInnings() {
        return new ArrayList<>(innings); // Return defensive copy
    }

    public Map<String, Score> getTeamScoreMap() {
        return new ConcurrentHashMap<>(teamScoreMap); // Return defensive copy
    }
}
