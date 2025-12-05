package org.hrsh.parkinglotmgmt;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class BillingService {
    private static final double TAX_RATE = 0.05; // 5% tax

    public BillReceipt generateBill(Spot spot, ParkingTicket parkingTicket, double additionalCharge) {
        if (spot == null || parkingTicket == null) {
            throw new IllegalArgumentException("Spot and ParkingTicket cannot be null");
        }

        Date entryDateTime = parkingTicket.getEntryDateTime();
        Date exitDateTime = new Date();

        double totalAmount = calculateAmount(additionalCharge, entryDateTime, exitDateTime,
                spot.getSpotType(), parkingTicket.getVehicle());

        return new BillReceipt(totalAmount, parkingTicket.getVehicle(), spot);
    }

    private double calculateAmount(double additionalCharge, Date entryDateTime,
                                   Date exitDateTime, SpotType spotType, Vehicle vehicle) {
        long hours = calculateHours(entryDateTime, exitDateTime);
        
        if (hours < 1) {
            hours = 1; // Minimum 1 hour charge
        }

        double spotBaseFare = getSpotBaseFare(spotType);
        double vehicleBaseFare = getVehicleBaseFare(vehicle);
        
        double baseAmount = hours * spotBaseFare * vehicleBaseFare;
        baseAmount += additionalCharge;
        
        double taxAmount = baseAmount * TAX_RATE;
        double totalAmount = baseAmount + taxAmount;

        return totalAmount;
    }

    /**
     * Calculate hours between two dates (rounded up)
     */
    private long calculateHours(Date start, Date end) {
        if (start == null || end == null || end.before(start)) {
            return 1; // Minimum 1 hour
        }
        
        Duration duration = Duration.between(
                start.toInstant(),
                end.toInstant()
        );
        
        long minutes = duration.toMinutes();
        // Round up to nearest hour
        return (minutes + 59) / 60;
    }

    private double getVehicleBaseFare(Vehicle vehicle) {
        if (vehicle == null || vehicle.getVehicleType() == null) {
            return 1.0; // Default fare
        }
        
        return switch (vehicle.getVehicleType()) {
            case TRUCK -> 6.0;
            case BUS -> 5.0;
            case CAR -> 4.0;
            case MOTORCYCLE -> 3.0;
        };
    }

    private double getSpotBaseFare(SpotType spotType) {
        if (spotType == null) {
            return 5.0; // Default fare
        }
        
        return switch (spotType) {
            case LARGE -> 10.0;
            case COMPACT -> 7.0;
            case MOTORCYCLE -> 5.0;
            case HANDICAPPED -> 3.0;
        };
    }
}
