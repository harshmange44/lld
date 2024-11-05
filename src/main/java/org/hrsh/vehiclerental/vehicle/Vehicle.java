package org.hrsh.vehiclerental.vehicle;

import org.hrsh.vehiclerental.ParkingStall;
import org.hrsh.vehiclerental.VehicleStatus;

import java.time.LocalDateTime;

public abstract class Vehicle {
    private String number;
    private String barcode;
    private String name;
    private String model;
    private boolean electricVehicle;
    private double pricePerDay;
    private int noOfSeats;
    private ParkingStall parkingStall;
    private LocalDateTime issuedAt;
    private VehicleStatus vehicleStatus;
    private boolean available;

    public abstract void init();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isElectricVehicle() {
        return electricVehicle;
    }

    public void setElectricVehicle(boolean electricVehicle) {
        this.electricVehicle = electricVehicle;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public ParkingStall getParkingStall() {
        return parkingStall;
    }

    public void setParkingStall(ParkingStall parkingStall) {
        this.parkingStall = parkingStall;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
