package org.hrsh.parkinglotmgmt;

import java.util.Date;
import java.util.UUID;

public class ParkingTicket {
    private final String id;
    private String customerName;
    private Date entryDateTime;
    private Vehicle vehicle;
    private Spot spot;
    private EntryPoint entryPoint;

    public ParkingTicket() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(Date entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }

    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }
}
