package org.hrsh.restaurantmgmt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Menu {
    private String id;
    private List<MenuItem> menuItems;

    public Menu() {
        this.id = UUID.randomUUID().toString();
        this.menuItems = new CopyOnWriteArrayList<>(); // Thread-safe
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MenuItem> getMenuItems() {
        return new ArrayList<>(menuItems); // Return defensive copy
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems != null ? new CopyOnWriteArrayList<>(menuItems) : new CopyOnWriteArrayList<>();
    }

    public void addMenuItem(MenuItem menuItem) {
        if (menuItem != null) {
            menuItems.add(menuItem);
        }
    }
}
