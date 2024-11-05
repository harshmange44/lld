package org.hrsh.cricinfo;

public class Score {
    private int runs;
    private int wickets;

    public Score(int runs, int wickets) {
        this.runs = runs;
        this.wickets = wickets;
    }

    public int getRuns() {
        return runs;
    }

    public void setRuns(int runs) {
        this.runs = runs;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }
}
