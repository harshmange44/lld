package org.hrsh.hotelmgmt;

public class Receptionist extends User {
    private int totalRoomsCheckedIn;

    public Receptionist(String name, String phone, Account account) {
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
