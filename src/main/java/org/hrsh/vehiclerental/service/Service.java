package org.hrsh.vehiclerental.service;

public abstract class Service {
    private String id;
    private String barcode;
    private String name;
    private double pricePerDay;
    private ServiceStatus equipmentStatus;

    public abstract String getUsage();
}
