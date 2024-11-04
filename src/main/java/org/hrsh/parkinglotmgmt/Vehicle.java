package org.hrsh.parkinglotmgmt;

public class Vehicle {
    private String name;
    private String number;
    private VehicleType vehicleType;
    private boolean isElectricVehicle;
    private Spot assignedSpot;

    public boolean isElectricVehicle() {
        return isElectricVehicle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Spot getAssignedSpot() {
        return assignedSpot;
    }

    public void setAssignedSpot(Spot assignedSpot) {
        this.assignedSpot = assignedSpot;
    }
}
