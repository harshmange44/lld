package org.hrsh.restaurantmgmt;

public class Staff extends User {
    private StaffRole staffRole;

    public Staff(String name, String phone, StaffRole staffRole) {
        super(name, phone);
        this.staffRole = staffRole;
    }

    public StaffRole getStaffRole() {
        return staffRole;
    }

    public void setStaffRole(StaffRole staffRole) {
        this.staffRole = staffRole;
    }
}
