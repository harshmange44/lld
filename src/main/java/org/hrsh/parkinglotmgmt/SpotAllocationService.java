package org.hrsh.parkinglotmgmt;

import java.util.List;
import java.util.stream.Collectors;

public class SpotAllocationService {
    /**
     * Service for finding and allocating parking spots based on vehicle type
     */

    public Spot findAvailableSpot(ParkingLot parkingLot, Vehicle vehicle) {
        if (parkingLot == null || vehicle == null) {
            return null;
        }

        List<Floor> floors = parkingLot.getFloors();
        
        // Strategy: Start from ground floor (index 0) and go up
        for (Floor floor : floors) {
            Spot spot = findAvailableSpotOnFloor(floor, vehicle);
            if (spot != null) {
                return spot;
            }
        }
        
        return null; // No available spot found
    }

    private Spot findAvailableSpotOnFloor(Floor floor, Vehicle vehicle) {
        List<Spot> availableSpots = floor.getSpots().stream()
                .filter(spot -> spot.getSpotStatus() == SpotStatus.AVAILABLE)
                .filter(spot -> isValidSpotForVehicle(spot, vehicle))
                .collect(Collectors.toList());

        if (availableSpots.isEmpty()) {
            return null;
        }

        // Return first available spot (could implement best-fit algorithm here)
        return availableSpots.get(0);
    }

    private boolean isValidSpotForVehicle(Spot spot, Vehicle vehicle) {
        // Electric vehicles need electric spots
        if (vehicle.isElectricVehicle() && !(spot instanceof ElectricVehicleSpot)) {
            return false;
        }

        // Check spot type compatibility with vehicle type
        VehicleType vehicleType = vehicle.getVehicleType();
        SpotType spotType = spot.getSpotType();

        switch (vehicleType) {
            case MOTORCYCLE:
                return spotType == SpotType.MOTORCYCLE;
            
            case CAR:
                // Car can park in COMPACT, LARGE, or HANDICAPPED spots
                return spotType == SpotType.COMPACT || 
                       spotType == SpotType.LARGE ||
                       spotType == SpotType.HANDICAPPED;
            
            case TRUCK:
            case BUS:
                // Large vehicles need LARGE spots
                return spotType == SpotType.LARGE;
            
            default:
                return false;
        }
    }
}

