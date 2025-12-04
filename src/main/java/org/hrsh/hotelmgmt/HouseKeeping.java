package org.hrsh.hotelmgmt;

import java.util.Date;
import java.util.UUID;

public class HouseKeeping {
    private String id;
    private String description;
    private Date startDateTime;
    private int durationInMin;
    private HouseKeeper houseKeeper;
    private boolean isCompleted;

    public HouseKeeping(String description, Date startDateTime, int durationInMin, HouseKeeper houseKeeper) {
        this.id = UUID.randomUUID().toString();
        this.description = description;
        this.startDateTime = startDateTime;
        this.durationInMin = durationInMin;
        this.houseKeeper = houseKeeper;
        this.isCompleted = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public int getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(int durationInMin) {
        this.durationInMin = durationInMin;
    }

    public HouseKeeper getHouseKeeper() {
        return houseKeeper;
    }

    public void setHouseKeeper(HouseKeeper houseKeeper) {
        this.houseKeeper = houseKeeper;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}
