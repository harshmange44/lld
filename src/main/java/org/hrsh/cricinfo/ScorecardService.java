package org.hrsh.cricinfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScorecardService {
    private Map<String, Scorecard> scorecardMap;

    public ScorecardService() {
        this.scorecardMap = new ConcurrentHashMap<>();
    }

    /**
     * Add scorecard to the service (should be called when match is created)
     */
    public void addScorecard(Scorecard scorecard) {
        if (scorecard == null) {
            throw new IllegalArgumentException("Scorecard cannot be null");
        }
        scorecardMap.put(scorecard.getId(), scorecard);
    }

    public synchronized void updateScorecard(String scorecardId, String teamId, Score score) {
        if (scorecardId == null || teamId == null || score == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        Scorecard scorecard = scorecardMap.get(scorecardId);

        if (scorecard == null) {
            throw new IllegalArgumentException("Scorecard not found");
        }
        scorecard.updateScore(teamId, score);
    }

    public synchronized void addInning(String scorecardId, Inning inning) {
        if (scorecardId == null || inning == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        Scorecard scorecard = scorecardMap.get(scorecardId);

        if (scorecard == null) {
            throw new IllegalArgumentException("Scorecard not found");
        }
        scorecard.addInning(inning);
    }

    public Scorecard getScorecard(String scorecardId) {
        if (scorecardId == null) {
            return null;
        }
        return scorecardMap.get(scorecardId);
    }

    /**
     * Get current score for a team
     */
    public Score getCurrentScore(String scorecardId, String teamId) {
        Scorecard scorecard = getScorecard(scorecardId);
        if (scorecard == null) {
            throw new IllegalArgumentException("Scorecard not found");
        }
        return scorecard.getCurrentScore(teamId);
    }
}
