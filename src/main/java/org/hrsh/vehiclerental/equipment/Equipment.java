package org.hrsh.vehiclerental.equipment;

public abstract class Equipment {
    private String id;
    private String barcode;
    private String name;
    private double pricePerDay;
    private EquipmentStatus equipmentStatus;

    public abstract String getUsage();
}
