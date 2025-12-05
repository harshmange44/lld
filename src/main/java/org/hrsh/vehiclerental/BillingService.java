package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.equipment.Equipment;
import org.hrsh.vehiclerental.service.Service;
import org.hrsh.vehiclerental.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BillingService {
    private static final double LATE_FEE_PER_DAY = 50.0; // $50 per day late fee
    private static final double INSURANCE_RATE = 0.15; // 15% insurance cost

    public double calculateTotalPrice(Vehicle vehicle, LocalDateTime startDate, LocalDateTime endDate,
                                     List<Equipment> equipmentList, List<Service> serviceList) {
        if (vehicle == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Vehicle and dates are required");
        }

        long days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
        if (days < 1) {
            days = 1; // Minimum 1 day
        }

        double vehicleCost = vehicle.getPricePerDay() * days;

        // Add equipment costs
        double equipmentCost = 0;
        if (equipmentList != null) {
            equipmentCost = equipmentList.stream()
                    .mapToDouble(eq -> eq.getPricePerDay() * days)
                    .sum();
        }

        // Add service costs
        double serviceCost = 0;
        if (serviceList != null) {
            serviceCost = serviceList.stream()
                    .mapToDouble(svc -> svc.getPricePerDay() * days)
                    .sum();
        }

        // Add insurance (15% of vehicle cost)
        double insuranceCost = vehicleCost * INSURANCE_RATE;

        return vehicleCost + equipmentCost + serviceCost + insuranceCost;
    }

    public BillReceipt generateBill(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        BillReceipt billReceipt = new BillReceipt(reservation.getTotalPrice(), List.of());
        billReceipt.setId(java.util.UUID.randomUUID().toString());
        return billReceipt;
    }

    public double calculateLateFee(Reservation reservation, LocalDateTime actualReturnDate) {
        if (reservation == null || actualReturnDate == null) {
            return 0;
        }

        LocalDateTime dueDate = reservation.getEndDate();
        if (actualReturnDate.isBefore(dueDate) || actualReturnDate.isEqual(dueDate)) {
            return 0; // No late fee
        }

        long lateDays = ChronoUnit.DAYS.between(dueDate.toLocalDate(), actualReturnDate.toLocalDate());
        return lateDays * LATE_FEE_PER_DAY;
    }

    public BillReceipt generateFinalBill(Reservation reservation, double lateFee) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null");
        }

        double totalAmount = reservation.getTotalPrice() + lateFee;

        BillReceipt billReceipt = new BillReceipt(totalAmount, List.of());
        billReceipt.setId(java.util.UUID.randomUUID().toString());
        return billReceipt;
    }
}

