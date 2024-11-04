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

    public ParkingTicket generateParkingTicket(Vehicle vehicle, String customerName) {
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setCustomerName(customerName);
        parkingTicket.setVehicle(vehicle);
        parkingTicket.setEntryDateTime(new Date());
        return parkingTicket;
    }

    public String showTotalAvailableSpots(List<Floor> floors) {
        int totalSpots = floors.stream().mapToInt(Floor::getAvailableSpots).sum();
        displayPanel.setDisplayMessage(String.format("Total available spots -> %s", totalSpots));
        return displayPanel.getDisplayMessage();
    }
}
