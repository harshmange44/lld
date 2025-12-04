package org.hrsh.hotelmgmt;

public class HouseKeeper extends User {
    public HouseKeeper(String name, String phone, Account account) {
        super(name, phone, account);
    }

    public void doHouseKeeping(Room room, HouseKeeping houseKeeping) {
        if (room == null || houseKeeping == null) {
            throw new IllegalArgumentException("Room and HouseKeeping cannot be null");
        }
        // Assign housekeeper to task
        houseKeeping.setHouseKeeper(this);
        // Mark as completed after duration
        houseKeeping.setCompleted(true);
        // Room status can be updated if needed
    }
}
