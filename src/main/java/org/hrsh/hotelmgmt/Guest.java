package org.hrsh.hotelmgmt;

public class Guest extends User {
    private int totalRoomsCheckedIn;

    public Guest(String name, String phone, Account account) {
        super(name, phone, account);
        this.totalRoomsCheckedIn = 0;
    }

    public int getTotalRoomsCheckedIn() {
        return totalRoomsCheckedIn;
    }

    public void setTotalRoomsCheckedIn(int totalRoomsCheckedIn) {
        this.totalRoomsCheckedIn = totalRoomsCheckedIn;
    }

    public void incrementCheckedInCount() {
        this.totalRoomsCheckedIn++;
    }
}
