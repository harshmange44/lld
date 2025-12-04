package org.hrsh.hotelmgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Hotel {
    private String hotelId;
    private String hotelName;
    private Location hotelLocation;
    private List<Room> roomList;

    public Hotel(String hotelName, Location hotelLocation) {
        this.hotelId = UUID.randomUUID().toString();
        this.hotelName = hotelName;
        this.hotelLocation = hotelLocation;
        this.roomList = new CopyOnWriteArrayList<>(); // Thread-safe
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public Location getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(Location hotelLocation) {
        this.hotelLocation = hotelLocation;
    }

    public List<Room> getRoomList() {
        return new ArrayList<>(roomList); // Return defensive copy
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList != null ? new CopyOnWriteArrayList<>(roomList) : new CopyOnWriteArrayList<>();
    }
}
