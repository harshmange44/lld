package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VehicleService {
    /**
     * Service for managing vehicle availability and searching
     */

    public List<Vehicle> searchAvailableVehicles(List<Vehicle> vehicles, 
                                                 Map<String, Reservation> reservations,
                                                 LocalDateTime startDate, LocalDateTime endDate) {
        if (vehicles == null || reservations == null || startDate == null || endDate == null) {
            return List.of();
        }

        return vehicles.stream()
                .filter(vehicle -> vehicle.isAvailable() 
                        && vehicle.getVehicleStatus() == VehicleStatus.WORKING)
                .filter(vehicle -> isVehicleAvailable(vehicle, reservations, startDate, endDate))
                .collect(Collectors.toList());
    }

    public boolean isVehicleAvailable(Vehicle vehicle, Map<String, Reservation> reservations,
                                     LocalDateTime startDate, LocalDateTime endDate) {
        if (vehicle == null || startDate == null || endDate == null) {
            return false;
        }

        // Check if vehicle is currently available
        if (!vehicle.isAvailable()) {
            return false;
        }

        // Check if vehicle is in working condition
        if (vehicle.getVehicleStatus() != VehicleStatus.WORKING) {
            return false;
        }

        // Check for date conflicts with existing reservations
        return reservations.values().stream()
                .filter(reservation -> reservation.getVehicle().equals(vehicle))
                .filter(reservation -> reservation.getReservationStatus() == ReservationStatus.CONFIRMED
                        || reservation.getReservationStatus() == ReservationStatus.PENDING)
                .noneMatch(reservation -> {
                    LocalDateTime resStart = reservation.getStartDate();
                    LocalDateTime resEnd = reservation.getEndDate();
                    // Check for overlap
                    return !(endDate.isBefore(resStart) || startDate.isAfter(resEnd));
                });
    }
}

