package org.hrsh.parkinglotmgmt;

public class ElectricPanel extends CustomerPortal {
    private String chargingStatus;
    private String chargingConsumption;

    public ElectricPanel(String id, String displayMessage) {
        super(id, displayMessage);
    }

    public String chargingStatus() {
        return chargingStatus;
    }
}
