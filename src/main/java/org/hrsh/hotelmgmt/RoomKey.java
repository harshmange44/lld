package org.hrsh.hotelmgmt;

import java.util.Date;
import java.util.UUID;

public class RoomKey {
    private String keyId;
    private String barcode;
    private Date issuedAt;
    private boolean isActive;
    private boolean isMasterKey;
    private Room assignedRoom;

    public RoomKey(String keyId, String barcode, Date issuedAt, boolean isActive, boolean isMasterKey) {
        this.keyId = keyId;
        this.barcode = barcode;
        this.issuedAt = issuedAt;
        this.isActive = isActive;
        this.isMasterKey = isMasterKey;
    }

    public boolean assignRoom(Room room) {
        if (room == null) {
            return false;
        }
        this.assignedRoom = room;
        return true;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isMasterKey() {
        return isMasterKey;
    }

    public void setMasterKey(boolean masterKey) {
        isMasterKey = masterKey;
    }

    public Room getAssignedRoom() {
        return assignedRoom;
    }

    public void setAssignedRoom(Room assignedRoom) {
        this.assignedRoom = assignedRoom;
    }
}
