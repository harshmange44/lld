package org.hrsh.hotelmgmt;

public class Admin extends User {
    public void addRoom(Room room) {

    }

    public void updateRoom(String roomId, Room room) {

    }

    public void deleteRoom(String roomId) {

    }

    public boolean issueKey(RoomKey roomKey) {
        return true;
    }

    public boolean updateKeyStatus(RoomKey roomKey, boolean isActive) {
        return true;
    }
}
