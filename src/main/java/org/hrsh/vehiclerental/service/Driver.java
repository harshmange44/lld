package org.hrsh.vehiclerental.service;

import org.hrsh.vehiclerental.DrivingLicense;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Driver extends Service {
    private List<DrivingLicense> drivingLicenseList;
    private boolean available;

    public Driver(String name, double pricePerDay) {
        super(name, pricePerDay);
        this.drivingLicenseList = new CopyOnWriteArrayList<>();
        this.available = true;
    }

    @Override
    public String getUsage() {
        return "Driver service for vehicle rental";
    }

    public List<DrivingLicense> getDrivingLicenseList() {
        return new ArrayList<>(drivingLicenseList);
    }

    public void setDrivingLicenseList(List<DrivingLicense> drivingLicenseList) {
        this.drivingLicenseList = drivingLicenseList != null 
                ? new CopyOnWriteArrayList<>(drivingLicenseList) 
                : new CopyOnWriteArrayList<>();
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
