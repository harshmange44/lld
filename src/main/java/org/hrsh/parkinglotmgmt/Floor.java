package org.hrsh.parkinglotmgmt;

import java.util.List;

public class Floor {
    private String floorId;
    private List<Spot> spots;
    private int availableSpots;
    private List<DisplayPanel> displayPanels;
    private List<CustomerPortal> customerPortals;

    public void setAvailableSpots() {
        displayPanels.forEach(displayPanel -> displayPanel.setDisplayMessage(String.format("Total available spots -> %s", availableSpots)));
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public List<DisplayPanel> getDisplayPanels() {
        return displayPanels;
    }

    public void setDisplayPanels(List<DisplayPanel> displayPanels) {
        this.displayPanels = displayPanels;
    }

    public List<CustomerPortal> getCustomerPortals() {
        return customerPortals;
    }

    public void setCustomerPortals(List<CustomerPortal> customerPortals) {
        this.customerPortals = customerPortals;
    }
}
