package org.hrsh.cricinfo;

import java.util.UUID;

public class Ball {
    private String id;
    private Player batter;
    private Player bowler;
    private int runsScored;
    private Wicket wicket;
    private BallLength ballLength;
    private DeliveryType deliveryType;

    public Ball(Player batter, Player bowler, int runsScored, Wicket wicket, BallLength ballLength, DeliveryType deliveryType) {
        this.id = UUID.randomUUID().toString();
        this.batter = batter;
        this.bowler = bowler;
        this.runsScored = runsScored;
        this.wicket = wicket;
        this.ballLength = ballLength;
        this.deliveryType = deliveryType;
    }

    public String getId() {
        return id;
    }

    public Player getBatter() {
        return batter;
    }

    public Player getBowler() {
        return bowler;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public Wicket getWicket() {
        return wicket;
    }

    public BallLength getBallLength() {
        return ballLength;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }
}
