package org.hrsh.cricinfo;

import java.util.ArrayList;
import java.util.List;

public class Over {
    private int overNumber;
    private List<Ball> balls;
    private boolean isSuperOver;

    public Over(int overNumber, boolean isSuperOver) {
        this.overNumber = overNumber;
        this.balls = new ArrayList<>();
        this.isSuperOver = isSuperOver;
    }

    public boolean addBall(Ball ball) {
        return balls.add(ball);
    }

    public int getOverNumber() {
        return overNumber;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public boolean isSuperOver() {
        return isSuperOver;
    }
}
