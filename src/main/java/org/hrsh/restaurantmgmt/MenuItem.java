package org.hrsh.restaurantmgmt;

import java.util.UUID;

public class MenuItem {
    private String id;
    private String name;
    private String desc;
    private double price;
    private boolean available;

    public MenuItem(String name, String desc, double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.available = true;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
