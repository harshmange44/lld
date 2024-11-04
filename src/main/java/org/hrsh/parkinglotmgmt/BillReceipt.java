package org.hrsh.parkinglotmgmt;

public class BillReceipt {
    private String id;
    private double totalAmount;
    private Vehicle vehicle;
    private Spot spot;

    public BillReceipt(double totalAmount, Vehicle vehicle, Spot spot) {
        this.totalAmount = totalAmount;
        this.vehicle = vehicle;
        this.spot = spot;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Spot getSpot() {
        return spot;
    }

    public void setSpot(Spot spot) {
        this.spot = spot;
    }
}
