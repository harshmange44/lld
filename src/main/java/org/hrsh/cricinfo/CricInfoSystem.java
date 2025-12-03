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
        if (match == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        matchService.addMatch(match);
        // Add scorecard to service when match is added
        scorecardService.addScorecard(match.getScorecard());
    }

    public Match getMatch(String matchId) {
        return matchService.getMatch(matchId);
    }

    public List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    public void updateMatchStatus(String matchId, MatchStatus status) {
        if (matchId == null || status == null) {
            throw new IllegalArgumentException("Match ID and status cannot be null");
        }
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

    /**
     * Record a ball (add ball to current over in an inning)
     */
    public void recordBall(String scorecardId, String inningId, Ball ball) {
        if (scorecardId == null || inningId == null || ball == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        Scorecard scorecard = scorecardService.getScorecard(scorecardId);
        if (scorecard == null) {
            throw new IllegalArgumentException("Scorecard not found");
        }
        
        Inning inning = scorecard.getInnings().stream()
                .filter(i -> i.getId().equals(inningId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Inning not found"));
        
        inning.addBallToCurrentOver(ball);
        
        // Auto-update score after ball is recorded
        String battingTeamId = inning.getBattingTeamId();
        Score currentScore = scorecard.getCurrentScore(battingTeamId);
        scorecardService.updateScorecard(scorecardId, battingTeamId, currentScore);
    }

    /**
     * Get current score for a team
     */
    public Score getCurrentScore(String scorecardId, String teamId) {
        return scorecardService.getCurrentScore(scorecardId, teamId);
    }
}
