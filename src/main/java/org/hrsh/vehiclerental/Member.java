package org.hrsh.vehiclerental;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Member extends User {
    private List<DrivingLicense> drivingLicenseList;

    public Member(String name, String phone) {
        super(name, phone);
        this.drivingLicenseList = new CopyOnWriteArrayList<>(); // Thread-safe
    }

    public List<DrivingLicense> getDrivingLicenseList() {
        return new ArrayList<>(drivingLicenseList); // Return defensive copy
    }

    public void setDrivingLicenseList(List<DrivingLicense> drivingLicenseList) {
        this.drivingLicenseList = drivingLicenseList != null 
                ? new CopyOnWriteArrayList<>(drivingLicenseList) 
                : new CopyOnWriteArrayList<>();
    }

    public void addDrivingLicense(DrivingLicense license) {
        if (license != null) {
            drivingLicenseList.add(license);
        }
    }
}
