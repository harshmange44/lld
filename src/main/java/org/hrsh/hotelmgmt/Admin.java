package org.hrsh.hotelmgmt;

public class Admin extends User {
    public Admin(String name, String phone, Account account) {
        super(name, phone, account);
    }

    // Room management methods would be called through HotelManagementSystem
    // Keeping this class for future extension if needed
}
