package org.hrsh.librarymgmt;

import java.util.Objects;

public class User {
    private String name;
    private String phone;
    private Account account;
    private Location location;

    public User(String name, String phone, Account account) {
        this.name = name;
        this.phone = phone;
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
