package org.hrsh.cricinfo;

public class Wicket {
    private int fallOn;
    private Ball ball;
    private Player player;
    private Player takenBy;
    private WicketType wicketType;

    public Wicket(int fallOn, Ball ball, Player player, Player takenBy, WicketType wicketType) {
        this.fallOn = fallOn;
        this.ball = ball;
        this.player = player;
        this.takenBy = takenBy;
        this.wicketType = wicketType;
    }

    public int getFallOn() {
        return fallOn;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getTakenBy() {
        return takenBy;
    }

    public WicketType getWicketType() {
        return wicketType;
    }
}
