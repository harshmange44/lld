package org.hrsh.airlinemgmt;

import java.util.UUID;

public class Aircraft {
    private String id;
    private String model;
    private int totalSeats;

    public Aircraft(String model, int totalSeats) {
        this.id = UUID.randomUUID().toString();
        this.model = model;
        this.totalSeats = totalSeats;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
