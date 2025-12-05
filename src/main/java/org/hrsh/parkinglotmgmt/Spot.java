package org.hrsh.parkinglotmgmt;

public class Spot {
    private String id;
    private SpotType spotType;
    private SpotStatus spotStatus;
    private Vehicle assignedVehicle;

    public Spot(String id, SpotType spotType, SpotStatus spotStatus, Vehicle assignedVehicle) {
        this.id = id;
        this.spotType = spotType;
        this.spotStatus = spotStatus != null ? spotStatus : SpotStatus.AVAILABLE;
        this.assignedVehicle = assignedVehicle;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        
        if (spotStatus == SpotStatus.AVAILABLE && isValidSpotType(vehicle)) {
            this.assignedVehicle = vehicle;
            this.spotStatus = SpotStatus.OCCUPIED;
            vehicle.setAssignedSpot(this);
            return true;
        }
        return false;
    }

    public synchronized boolean freeVehicle() {
        if (assignedVehicle == null) {
            return false; // No vehicle assigned
        }
        
        this.spotStatus = SpotStatus.AVAILABLE;
        if (assignedVehicle != null) {
            assignedVehicle.setAssignedSpot(null);
        }
        this.assignedVehicle = null;
        return true;
    }

    private boolean isValidSpotType(Vehicle vehicle) {
        if (vehicle == null) {
            return false;
        }
        
        // Electric vehicles need electric spots
        if (vehicle.isElectricVehicle() && !(this instanceof ElectricVehicleSpot)) {
            return false;
        }

        // Check spot type compatibility with vehicle type
        VehicleType vehicleType = vehicle.getVehicleType();
        if (vehicleType == null) {
            return false;
        }

        // Cars cannot park in motorcycle or handicapped spots (unless it's handicapped)
        if (vehicleType == VehicleType.CAR && 
            (spotType == SpotType.MOTORCYCLE)) {
            return false;
        }

        // Motorcycles can only park in motorcycle spots
        if (vehicleType == VehicleType.MOTORCYCLE && spotType != SpotType.MOTORCYCLE) {
            return false;
        }

        // Large vehicles need large spots
        if ((vehicleType == VehicleType.TRUCK || vehicleType == VehicleType.BUS) 
            && spotType != SpotType.LARGE) {
            return false;
        }

        return true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public SpotStatus getSpotStatus() {
        return spotStatus;
    }

    public void setSpotStatus(SpotStatus spotStatus) {
        this.spotStatus = spotStatus;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }
}
