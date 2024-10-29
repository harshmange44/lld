package org.hrsh.hotelmgmt;

import java.util.Date;

public class RoomKey {
    private String keyId;
    private String barcode;
    private Date issuedAt;
    private boolean isActive;
    private boolean isMasterKey;

    public boolean assignRoom(Room room) {
        return true;
    }
}
