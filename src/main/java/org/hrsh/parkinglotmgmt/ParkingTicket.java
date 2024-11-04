package org.hrsh.parkinglotmgmt;

import java.util.Date;
import java.util.UUID;

public class ParkingTicket {
    private final String id;
    private String customerName;
    private Date entryDateTime;
    private Vehicle vehicle;

    public ParkingTicket() {
        this.id = UUID.randomUUID().toString();
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
}
