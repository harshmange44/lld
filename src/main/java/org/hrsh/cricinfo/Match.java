package org.hrsh.cricinfo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Match {
    private String id;
    private String title;
    private Location venue;
    private LocalDateTime startTime;
    private List<Team> teams;
    private MatchStatus matchStatus;
    private Scorecard scorecard;

    public Match(String title, Location venue, LocalDateTime startTime) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.venue = venue;
        this.startTime = startTime;
        this.matchStatus = MatchStatus.SCHEDULED;
        this.scorecard = new Scorecard(this);
    }

    public void initTeams(Team team1, Team team2) {
        teams.add(team1);
        teams.add(team2);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Location getVenue() {
        return venue;
    }

    public void setVenue(Location venue) {
        this.venue = venue;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    public Scorecard getScorecard() {
        return scorecard;
    }

    public void setScorecard(Scorecard scorecard) {
        this.scorecard = scorecard;
    }
}
