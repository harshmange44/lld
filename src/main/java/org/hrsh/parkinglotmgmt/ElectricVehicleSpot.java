package org.hrsh.parkinglotmgmt;

public class ElectricVehicleSpot extends Spot {
    private ElectricPanel electricPanel;

    public ElectricVehicleSpot(String id, SpotType spotType, SpotStatus spotStatus, Vehicle assignedVehicle) {
        super(id, spotType, spotStatus, assignedVehicle);
    }
}
