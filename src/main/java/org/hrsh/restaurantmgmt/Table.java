package org.hrsh.restaurantmgmt;

import java.time.LocalDateTime;

public class Table {
    private String id;
    private int capacity;
    private boolean isReserved;
    private LocalDateTime reservedUntil;

    public Table(String id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.isReserved = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }

    public LocalDateTime getReservedUntil() {
        return reservedUntil;
    }

    public void setReservedUntil(LocalDateTime reservedUntil) {
        this.reservedUntil = reservedUntil;
    }
}

