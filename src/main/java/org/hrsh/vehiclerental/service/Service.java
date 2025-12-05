package org.hrsh.vehiclerental.service;

import java.util.UUID;

public abstract class Service {
    private String id;
    private String barcode;
    private String name;
    private double pricePerDay;
    private ServiceStatus serviceStatus;

    protected Service(String name, double pricePerDay) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.pricePerDay = pricePerDay;
        this.serviceStatus = ServiceStatus.AVAILABLE;
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

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
