package org.hrsh.parkinglotmgmt;

public class Spot {
    private String id;
    private SpotType spotType;
    private SpotStatus spotStatus;
    private Vehicle assignedVehicle;

    public Spot(String id, SpotType spotType, SpotStatus spotStatus, Vehicle assignedVehicle) {
        this.id = id;
        this.spotType = spotType;
        this.spotStatus = spotStatus;
        this.assignedVehicle = assignedVehicle;
    }

    public boolean assignVehicle(Vehicle vehicle) {
        if (spotStatus == SpotStatus.AVAILABLE && isValidSpotType(vehicle)) {
            this.assignedVehicle = vehicle;
            this.spotStatus = SpotStatus.OCCUPIED;
            vehicle.setAssignedSpot(this);
            return true;
        }
        return false;
    }

    public boolean freeVehicle() {
        this.spotStatus = SpotStatus.AVAILABLE;
        this.assignedVehicle.setAssignedSpot(null);
        this.assignedVehicle = null;
        return true;
    }

    private boolean isValidSpotType(Vehicle vehicle) {
        if (vehicle.isElectricVehicle() && !(this instanceof ElectricVehicleSpot)) return false;

        if (vehicle.getVehicleType() == VehicleType.CAR && (spotType == SpotType.MOTORCYCLE || spotType == SpotType.HANDICAPPED)) {
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
