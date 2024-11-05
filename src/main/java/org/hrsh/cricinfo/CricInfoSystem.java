package org.hrsh.cricinfo;

import java.util.List;

public class CricInfoSystem {
    /**
     * 1. Multiple Matches
     * 2. Multiple Teams
     * 3. Multiple Players
     * 4. Update Innings
     * 5. Update Scorecard
    */

    private final MatchService matchService;
    private final ScorecardService scorecardService;

    public CricInfoSystem() {
        this.matchService = new MatchService();
        this.scorecardService = new ScorecardService();
    }

    public void addMatch(Match match) {
        matchService.addMatch(match);
    }

    public Match getMatch(String matchId) {
        return matchService.getMatch(matchId);
    }

    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    public void updateMatchStatus(String matchId, MatchStatus status) {
        matchService.updateMatchStatus(matchId, status);
    }

    public Scorecard getScorecard(String scorecardId) {
        return scorecardService.getScorecard(scorecardId);
    }

    public void updateScore(String scorecardId, String teamId, Score score) {
        scorecardService.updateScorecard(scorecardId, teamId, score);
    }

    public void addInnings(String scorecardId, Inning inning) {
        scorecardService.addInning(scorecardId, inning);
    }
}
