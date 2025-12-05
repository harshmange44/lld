package org.hrsh.linkedin;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String phone;
    private Media profilePhoto;
    private Location location;
    private Account account;

    public User(String name, String phone, Account account) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Media getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Media profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
