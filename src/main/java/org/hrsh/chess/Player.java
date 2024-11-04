package org.hrsh.chess;

public class Player {
    private String username;
    private Color color;
    private double timeInMins;

    public Player(String username, Color color, double timeInMins) {
        this.username = username;
        this.color = color;
        this.timeInMins = timeInMins;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getTimeInMins() {
        return timeInMins;
    }

    public void setTimeInMins(double timeInMins) {
        this.timeInMins = timeInMins;
    }
}
