package org.hrsh.parkinglotmgmt;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private String id;
    private String name;
    private Location location;
    private int capacity;
    private List<Floor> floorList;
    private List<EntryPoint> entryPointList;
    private List<ExitPoint> exitPointList;

    public ParkingLot(String name, Location location) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.floorList = new CopyOnWriteArrayList<>();
        this.entryPointList = new CopyOnWriteArrayList<>();
        this.exitPointList = new CopyOnWriteArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Floor> getFloors() {
        return floorList; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setFloorList(List<Floor> floorList) {
        this.floorList = floorList != null ? new CopyOnWriteArrayList<>(floorList) : new CopyOnWriteArrayList<>();
    }

    public List<EntryPoint> getEntryPoints() {
        return entryPointList; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setEntryPointList(List<EntryPoint> entryPointList) {
        this.entryPointList = entryPointList != null ? new CopyOnWriteArrayList<>(entryPointList) : new CopyOnWriteArrayList<>();
    }

    public List<ExitPoint> getExitPoints() {
        return exitPointList; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setExitPointList(List<ExitPoint> exitPointList) {
        this.exitPointList = exitPointList != null ? new CopyOnWriteArrayList<>(exitPointList) : new CopyOnWriteArrayList<>();
    }
}
