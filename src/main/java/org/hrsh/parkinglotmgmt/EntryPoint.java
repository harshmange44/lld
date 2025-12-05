package org.hrsh.parkinglotmgmt;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EntryPoint {
    private final String id;
    private final DisplayPanel displayPanel;

    public EntryPoint(DisplayPanel displayPanel) {
        this.id = UUID.randomUUID().toString();
        this.displayPanel = displayPanel;
    }

    public String getId() {
        return id;
    }

    public ParkingTicket generateParkingTicket(Vehicle vehicle, String customerName) {
        if (vehicle == null || customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Vehicle and customer name are required");
        }
        
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setCustomerName(customerName);
        parkingTicket.setVehicle(vehicle);
        parkingTicket.setEntryDateTime(new Date());
        return parkingTicket;
    }

    public String showTotalAvailableSpots(List<Floor> floors) {
        if (floors == null || floors.isEmpty()) {
            return "No floors available";
        }
        
        int totalSpots = floors.stream()
                .mapToInt(Floor::getAvailableSpots)
                .sum();
        
        String message = String.format("Total available spots: %s", totalSpots);
        
        if (displayPanel != null) {
            displayPanel.setDisplayMessage(message);
        }
        
        return message;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }
}
