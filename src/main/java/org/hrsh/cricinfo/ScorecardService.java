package org.hrsh.cricinfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ScorecardService {
    private Map<String, Scorecard> scorecardMap;

    public ScorecardService() {
        this.scorecardMap = new ConcurrentHashMap<>();
    }

    public synchronized void updateScorecard(String scorecardId, String teamId, Score score) {
        Scorecard scorecard = scorecardMap.get(scorecardId);

        if (scorecard != null) {
            scorecard.updateScore(teamId, score);
        }
    }

    public synchronized void addInning(String scorecardId, Inning inning) {
        Scorecard scorecard = scorecardMap.get(scorecardId);

        if (scorecard != null) {
            scorecard.addInning(inning);
        }
    }

    public Scorecard getScorecard(String scorecardId) {
        return scorecardMap.get(scorecardId);
    }
}
