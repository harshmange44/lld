package org.hrsh.cricinfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        this.teams = new ArrayList<>(); // Fixed: Initialize teams list
        this.scorecard = new Scorecard(this);
    }

    public void initTeams(Team team1, Team team2) {
        if (team1 == null || team2 == null) {
            throw new IllegalArgumentException("Teams cannot be null");
        }
        if (teams.size() >= 2) {
            throw new IllegalStateException("Teams already initialized");
        }
        teams.clear();
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
        return new ArrayList<>(teams); // Return defensive copy
    }

    public void setTeams(List<Team> teams) {
        if (teams == null || teams.size() != 2) {
            throw new IllegalArgumentException("Match must have exactly 2 teams");
        }
        this.teams = new ArrayList<>(teams);
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
