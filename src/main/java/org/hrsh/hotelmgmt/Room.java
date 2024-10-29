package org.hrsh.hotelmgmt;

import java.util.List;

public class Room {
    private String roomId;
    private String roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private double roomPrice;
    private List<RoomKey> roomKeys;
    private List<HouseKeeping> houseKeepingLogs;
}
