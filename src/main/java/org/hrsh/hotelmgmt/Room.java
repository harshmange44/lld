package org.hrsh.hotelmgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Room {
    private String roomId;
    private String roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private double roomPrice;
    private List<RoomKey> roomKeys;
    private List<HouseKeeping> houseKeepingLogs;

    public Room(String roomNumber, RoomType roomType, double roomPrice) {
        this.roomId = UUID.randomUUID().toString();
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = RoomStatus.AVAILABLE;
        this.roomPrice = roomPrice;
        this.roomKeys = new CopyOnWriteArrayList<>(); // Thread-safe
        this.houseKeepingLogs = new CopyOnWriteArrayList<>(); // Thread-safe
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public List<RoomKey> getRoomKeys() {
        return new ArrayList<>(roomKeys); // Return defensive copy
    }

    public void setRoomKeys(List<RoomKey> roomKeys) {
        this.roomKeys = roomKeys != null ? new CopyOnWriteArrayList<>(roomKeys) : new CopyOnWriteArrayList<>();
    }

    public List<HouseKeeping> getHouseKeepingLogs() {
        return new ArrayList<>(houseKeepingLogs); // Return defensive copy
    }

    public void setHouseKeepingLogs(List<HouseKeeping> houseKeepingLogs) {
        this.houseKeepingLogs = houseKeepingLogs != null ? new CopyOnWriteArrayList<>(houseKeepingLogs) : new CopyOnWriteArrayList<>();
    }
}
