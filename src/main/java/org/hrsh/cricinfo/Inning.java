package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Inning {
    private String id;
    private String battingTeamId;
    private String bowlingTeamId;
    private List<Over> overs;

    public Inning(String battingTeamId, String bowlingTeamId) {
        if (battingTeamId == null || bowlingTeamId == null) {
            throw new IllegalArgumentException("Team IDs cannot be null");
        }
        this.id = UUID.randomUUID().toString();
        this.battingTeamId = battingTeamId;
        this.bowlingTeamId = bowlingTeamId;
        this.overs = new CopyOnWriteArrayList<>(); // Thread-safe list
    }

    public synchronized boolean addOver(Over over) {
        if (over == null) {
            throw new IllegalArgumentException("Over cannot be null");
        }
        // Validate over number is sequential
        if (!overs.isEmpty()) {
            int lastOverNumber = overs.get(overs.size() - 1).getOverNumber();
            if (over.getOverNumber() <= lastOverNumber) {
                throw new IllegalArgumentException("Over number must be sequential");
            }
        }
        return overs.add(over);
    }

    /**
     * Add a ball to the current over (last over)
     */
    public synchronized boolean addBallToCurrentOver(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("Ball cannot be null");
        }
        if (overs.isEmpty()) {
            throw new IllegalStateException("No over exists. Create an over first.");
        }
        Over currentOver = overs.get(overs.size() - 1);
        if (currentOver.getBalls().size() >= 6 && ball.getDeliveryType().equals(DeliveryType.NORMAL_BALL)) {
            throw new IllegalStateException("Over is complete. Maximum 6 balls per over.");
        }
        return currentOver.addBall(ball);
    }

    /**
     * Calculate total runs from all balls in all overs
     */
    public int calculateTotalRuns() {
        int totalRuns = 0;
        for (Over over : overs) {
            totalRuns += over.calculateTotalRuns();
        }
        return totalRuns;
    }

    /**
     * Calculate total wickets from all balls
     */
    public int calculateTotalWickets() {
        int totalWickets = 0;
        for (Over over : overs) {
            totalWickets += over.calculateTotalWickets();
        }
        return totalWickets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(String battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(String bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public List<Over> getOvers() {
        return new ArrayList<>(overs); // Return defensive copy
    }

    public void setOvers(List<Over> overs) {
        this.overs = overs != null ? new CopyOnWriteArrayList<>(overs) : new CopyOnWriteArrayList<>();
    }
}
