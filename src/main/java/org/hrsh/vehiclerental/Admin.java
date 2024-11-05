package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.vehicle.Vehicle;

public class Admin extends User {
    public boolean addVehicle(Vehicle vehicle) {
        return true;
    }

    public boolean editVehicle(String vehicleBarcode, Vehicle vehicle) {
        return true;
    }
}
