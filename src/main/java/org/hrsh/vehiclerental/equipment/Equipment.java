package org.hrsh.vehiclerental.equipment;

import java.util.UUID;

public abstract class Equipment {
    private String id;
    private String barcode;
    private String name;
    private double pricePerDay;
    private EquipmentStatus equipmentStatus;

    protected Equipment(String name, double pricePerDay) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.equipmentStatus = EquipmentStatus.AVAILABLE;
    }

    public abstract String getUsage();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public EquipmentStatus getEquipmentStatus() {
        return equipmentStatus;
    }

    public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
        this.equipmentStatus = equipmentStatus;
    }
}
