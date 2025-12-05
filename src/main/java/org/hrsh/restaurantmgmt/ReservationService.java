package org.hrsh.restaurantmgmt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReservationService {
    /**
     * Service for managing restaurant reservations and table allocation
     */

    public Reservation createReservation(Restaurant restaurant, String customerName, 
                                        String customerPhone, int totalPeople, 
                                        LocalDateTime reservationDate, Table table) {
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setCustomerName(customerName);
        reservation.setCustomerPhone(customerPhone);
        reservation.setTotalPeople(totalPeople);
        reservation.setReservationDate(reservationDate);
        reservation.setReservationStatus(ReservationStatus.RESERVED);
        reservation.setTable(table);

        // Assign table to reservation
        table.setReserved(true);
        table.setReservedUntil(reservationDate.plusHours(2)); // 2 hour reservation window

        return reservation;
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation == null) {
            return;
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);

        // Free the table
        Table table = reservation.getTable();
        if (table != null) {
            table.setReserved(false);
            table.setReservedUntil(null);
        }
    }

    public Table findAvailableTable(Restaurant restaurant, int totalPeople, LocalDateTime reservationDate) {
        if (restaurant == null || totalPeople <= 0 || reservationDate == null) {
            return null;
        }

        List<Table> tables = restaurant.getTables();
        if (tables == null || tables.isEmpty()) {
            return null;
        }

        // Find available table that can accommodate the party
        List<Table> availableTables = tables.stream()
                .filter(table -> !table.isReserved() || isTableAvailableAtTime(table, reservationDate))
                .filter(table -> table.getCapacity() >= totalPeople)
                .sorted((t1, t2) -> Integer.compare(t1.getCapacity(), t2.getCapacity())) // Prefer smaller tables
                .collect(Collectors.toList());

        if (availableTables.isEmpty()) {
            return null;
        }

        return availableTables.get(0); // Return first available table
    }

    private boolean isTableAvailableAtTime(Table table, LocalDateTime reservationDate) {
        if (table.getReservedUntil() == null) {
            return true;
        }
        // Table is available if reservation time is after the current reservation ends
        return reservationDate.isAfter(table.getReservedUntil());
    }
}

