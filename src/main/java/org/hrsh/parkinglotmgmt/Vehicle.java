package org.hrsh.parkinglotmgmt;

public class Vehicle {
    private String name;
    private String number;
    private VehicleType vehicleType;
    private boolean isElectricVehicle;
    private Spot assignedSpot;

    public Vehicle(String name, String number, VehicleType vehicleType, boolean isElectricVehicle) {
        this.name = name;
        this.number = number;
        this.vehicleType = vehicleType;
        this.isElectricVehicle = isElectricVehicle;
    }

    public boolean isElectricVehicle() {
        return isElectricVehicle;
    }

    public void setElectricVehicle(boolean electricVehicle) {
        isElectricVehicle = electricVehicle;
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
