package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Over {
    private int overNumber;
    private List<Ball> balls;
    private boolean isSuperOver;

    public Over(int overNumber, boolean isSuperOver) {
        if (overNumber < 1) {
            throw new IllegalArgumentException("Over number must be positive");
        }
        this.overNumber = overNumber;
        this.balls = new CopyOnWriteArrayList<>(); // Thread-safe list
        this.isSuperOver = isSuperOver;
    }

    public synchronized boolean addBall(Ball ball) {
        if (ball == null) {
            throw new IllegalArgumentException("Ball cannot be null");
        }
        // Validate max 6 legal balls per over (wides and no-balls don't count)
        long legalBalls = balls.stream()
                .filter(b -> b.getDeliveryType().equals(DeliveryType.WIDE_BALL) 
                        && b.getDeliveryType().equals(DeliveryType.NORMAL_BALL))
                .count();
        
        if (legalBalls >= 6 && ball.getDeliveryType().equals(DeliveryType.NORMAL_BALL)) {
            throw new IllegalStateException("Over is complete. Maximum 6 legal balls per over.");
        }
        return balls.add(ball);
    }

    /**
     * Calculate total runs from all balls in this over
     */
    public int calculateTotalRuns() {
        return balls.stream()
                .mapToInt(Ball::getRunsScored)
                .sum();
    }

    /**
     * Calculate total wickets in this over
     */
    public int calculateTotalWickets() {
        return (int) balls.stream()
                .filter(ball -> ball.getWicket() != null)
                .count();
    }

    public int getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(int overNumber) {
        this.overNumber = overNumber;
    }

    public List<Ball> getBalls() {
        return new ArrayList<>(balls); // Return defensive copy
    }

    public void setBalls(List<Ball> balls) {
        this.balls = balls != null ? new CopyOnWriteArrayList<>(balls) : new CopyOnWriteArrayList<>();
    }

    public boolean isSuperOver() {
        return isSuperOver;
    }

    public void setSuperOver(boolean superOver) {
        isSuperOver = superOver;
    }
}
