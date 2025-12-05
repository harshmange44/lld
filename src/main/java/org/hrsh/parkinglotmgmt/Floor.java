package org.hrsh.parkinglotmgmt;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Floor {
    private String floorId;
    private List<Spot> spots;
    private int availableSpots;
    private List<DisplayPanel> displayPanels;
    private List<CustomerPortal> customerPortals;

    public Floor(String floorId) {
        this.floorId = floorId != null ? floorId : UUID.randomUUID().toString();
        this.spots = new CopyOnWriteArrayList<>();
        this.displayPanels = new CopyOnWriteArrayList<>();
        this.customerPortals = new CopyOnWriteArrayList<>();
        this.availableSpots = 0;
    }

    public void setAvailableSpots() {
        long availableCount = spots.stream()
                .filter(spot -> spot.getSpotStatus() == SpotStatus.AVAILABLE)
                .count();
        this.availableSpots = (int) availableCount;
        
        // Update all display panels on this floor
        if (displayPanels != null) {
            displayPanels.forEach(displayPanel -> 
                displayPanel.setDisplayMessage(String.format("Floor %s - Available spots: %s", floorId, availableSpots)));
        }
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public List<Spot> getSpots() {
        return spots; // Return directly as CopyOnWriteArrayList is thread-safe
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots != null ? new CopyOnWriteArrayList<>(spots) : new CopyOnWriteArrayList<>();
        setAvailableSpots(); // Recalculate available spots
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public List<DisplayPanel> getDisplayPanels() {
        return displayPanels != null ? new CopyOnWriteArrayList<>(displayPanels) : new CopyOnWriteArrayList<>();
    }

    public void setDisplayPanels(List<DisplayPanel> displayPanels) {
        this.displayPanels = displayPanels != null ? new CopyOnWriteArrayList<>(displayPanels) : new CopyOnWriteArrayList<>();
    }

    public List<CustomerPortal> getCustomerPortals() {
        return customerPortals != null ? new CopyOnWriteArrayList<>(customerPortals) : new CopyOnWriteArrayList<>();
    }

    public void setCustomerPortals(List<CustomerPortal> customerPortals) {
        this.customerPortals = customerPortals != null ? new CopyOnWriteArrayList<>(customerPortals) : new CopyOnWriteArrayList<>();
    }
}
