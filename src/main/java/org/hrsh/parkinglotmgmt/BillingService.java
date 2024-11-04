package org.hrsh.parkinglotmgmt;

import java.util.Date;

public class BillingService {
    public BillReceipt generateBill(Spot spot, ParkingTicket parkingTicket, double additionalCharge) {
        Date entryDateTime = parkingTicket.getEntryDateTime();
        Date exitDateTime = new Date();

        return new BillReceipt(calculateAmount(additionalCharge, entryDateTime, exitDateTime,
                spot.getSpotType(), parkingTicket.getVehicle()),
                parkingTicket.getVehicle(), spot);
    }

    private double calculateAmount(double additionalCharge, Date entryDateTime,
                                   Date exitDateTime, SpotType spotType, Vehicle vehicle) {
        double amount = 0;
        double spotBaseFare = getSpotBaseFare(spotType);
        double vehicleBaseFare = getVehicleBaseFare(vehicle);
        amount += (exitDateTime.getHours() - entryDateTime.getHours())*spotBaseFare*vehicleBaseFare;
        amount += additionalCharge;
        amount += calculateTaxAmount(amount);
        return amount;
    }

    private double calculateTaxAmount(double amount) {
        return amount*5/100;
    }

    private double getVehicleBaseFare(Vehicle vehicle) {
        return switch (vehicle.getVehicleType()) {
            case TRUCK -> 6;
            case BUS -> 5;
            case CAR -> 4;
            case MOTORCYCLE -> 3;
        };
    }

    private double getSpotBaseFare(SpotType spotType) {
        return switch (spotType) {
            case LARGE -> 10;
            case COMPACT -> 7;
            case MOTORCYCLE -> 5;
            case HANDICAPPED -> 3;
        };
    }
}
