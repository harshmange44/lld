package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.equipment.Equipment;
import org.hrsh.vehiclerental.service.Driver;
import org.hrsh.vehiclerental.service.Service;
import org.hrsh.vehiclerental.vehicle.Vehicle;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RentalSystem {
    /**
     * Main orchestrator for Vehicle Rental System
     * Handles: Reservation, Vehicle Availability, Payment, Returns, Late Fees
     */

    private final List<Vehicle> vehicleList;
    private final List<Member> memberList;
    private final List<Admin> adminList;
    private final Map<String, Reservation> reservationMap; // reservationId -> Reservation
    private final List<Driver> driverList;
    private final List<ParkingStall> parkingStallList;
    private final List<Equipment> equipmentList;
    private final List<Service> serviceList;

    private final PaymentService paymentService;
    private final VehicleService vehicleService;
    private final BillingService billingService;
    private final NotificationService notificationService;

    public RentalSystem() {
        this.paymentService = new PaymentService();
        this.vehicleService = new VehicleService();
        this.billingService = new BillingService();
        this.notificationService = new NotificationService();
        
        this.vehicleList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.memberList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.adminList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.reservationMap = new ConcurrentHashMap<>();
        this.driverList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.parkingStallList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.equipmentList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.serviceList = new CopyOnWriteArrayList<>(); // Thread-safe
        
        // Start late fee checking service
        startLateFeeCheckService();
    }

    // Member Management
    public void registerMember(Member member) {
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        memberList.add(member);
    }

    public Member getMember(String memberId) {
        return memberList.stream()
                .filter(m -> m.getId().equals(memberId))
                .findFirst()
                .orElse(null);
    }

    // Vehicle Management
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        vehicleList.add(vehicle);
    }

    public List<Vehicle> searchAvailableVehicles(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return vehicleService.searchAvailableVehicles(vehicleList, reservationMap, startDate, endDate);
    }

    // Reservation Management
    public synchronized Reservation reserveVehicle(String memberId, String vehicleBarcode, 
                                                   LocalDateTime startDate, LocalDateTime endDate,
                                                   List<Equipment> equipmentList, 
                                                   List<Service> serviceList) {
        if (memberId == null || vehicleBarcode == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("All required parameters must be provided");
        }

        if (startDate.isAfter(endDate) || startDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid reservation dates");
        }

        Member member = getMember(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }

        // Find vehicle
        Vehicle vehicle = vehicleList.stream()
                .filter(v -> v.getBarcode().equals(vehicleBarcode))
                .findFirst()
                .orElse(null);

        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        // Check vehicle availability
        if (!vehicleService.isVehicleAvailable(vehicle, reservationMap, startDate, endDate)) {
            throw new IllegalStateException("Vehicle is not available for the selected dates");
        }

        // Calculate total price
        double totalPrice = billingService.calculateTotalPrice(
                vehicle, startDate, endDate, equipmentList, serviceList);

        // Create reservation
        Reservation reservation = new Reservation();
        reservation.setId(UUID.randomUUID().toString());
        reservation.setVehicle(vehicle);
        reservation.setMember(member);
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setEquipmentList(equipmentList != null ? new ArrayList<>(equipmentList) : new ArrayList<>());
        reservation.setServiceList(serviceList != null ? new ArrayList<>(serviceList) : new ArrayList<>());
        reservation.setTotalPrice(totalPrice);
        reservation.setReservationStatus(ReservationStatus.PENDING);

        // Store reservation
        reservationMap.put(reservation.getId(), reservation);

        // Send notification
        notificationService.sendNotification(member, NotificationType.RESERVATION_CREATED,
                "Reservation created for vehicle " + vehicle.getName() + " from " + startDate.toLocalDate() 
                + " to " + endDate.toLocalDate());

        return reservation;
    }

    // Payment and Vehicle Acceptance
    public synchronized PaymentReceipt acceptVehicle(String reservationId, PaymentMethod paymentMethod) {
        if (reservationId == null || paymentMethod == null) {
            throw new IllegalArgumentException("Reservation ID and payment method are required");
        }

        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        if (reservation.getReservationStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Reservation is not in PENDING status");
        }

        // Generate bill
        BillReceipt billReceipt = billingService.generateBill(reservation);

        // Process payment
        PaymentReceipt paymentReceipt = paymentService.process(billReceipt, paymentMethod);

        if (paymentReceipt.getPaymentStatus() == PaymentStatus.SUCCESSFUL) {
            // Update reservation status
            reservation.setReservationStatus(ReservationStatus.CONFIRMED);

            // Mark vehicle as unavailable
            reservation.getVehicle().setAvailable(false);

            // Send notification
            notificationService.sendNotification(reservation.getMember(), NotificationType.RESERVATION_CONFIRMED,
                    "Reservation confirmed. Payment successful. Amount: $" + billReceipt.getTotalAmount());
        } else {
            notificationService.sendNotification(reservation.getMember(), NotificationType.PAYMENT_FAILED,
                    "Payment failed. Please try again.");
        }

        return paymentReceipt;
    }

    // Return Vehicle
    public synchronized BillReceipt returnVehicle(String reservationId, LocalDateTime returnDate) {
        if (reservationId == null || returnDate == null) {
            throw new IllegalArgumentException("Reservation ID and return date are required");
        }

        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation not found");
        }

        if (reservation.getReservationStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalStateException("Reservation is not confirmed");
        }

        // Calculate late fees if applicable
        double lateFee = billingService.calculateLateFee(reservation, returnDate);

        // Update reservation
        reservation.setReservationStatus(ReservationStatus.COMPLETED);
        reservation.setActualReturnDate(returnDate);

        // Mark vehicle as available
        reservation.getVehicle().setAvailable(true);

        // Generate final bill with late fees
        BillReceipt finalBill = billingService.generateFinalBill(reservation, lateFee);

        // Send notification
        String message = lateFee > 0 
                ? "Vehicle returned. Late fee: $" + lateFee + ". Total: $" + finalBill.getTotalAmount()
                : "Vehicle returned successfully";
        notificationService.sendNotification(reservation.getMember(), NotificationType.VEHICLE_RETURNED, message);

        return finalBill;
    }

    // Cancel Reservation
    public synchronized boolean cancelReservation(String reservationId) {
        if (reservationId == null) {
            throw new IllegalArgumentException("Reservation ID cannot be null");
        }

        Reservation reservation = reservationMap.get(reservationId);
        if (reservation == null) {
            return false;
        }

        if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed reservation");
        }

        reservation.setReservationStatus(ReservationStatus.CANCELLED);

        // If vehicle was marked unavailable, make it available again
        if (!reservation.getVehicle().isAvailable()) {
            reservation.getVehicle().setAvailable(true);
        }

        // Send notification
        notificationService.sendNotification(reservation.getMember(), NotificationType.RESERVATION_CANCELLED,
                "Reservation cancelled successfully");

        return true;
    }

    // Get reservations
    public Reservation getReservation(String reservationId) {
        return reservationMap.get(reservationId);
    }

    public List<Reservation> getMemberReservations(String memberId) {
        return reservationMap.values().stream()
                .filter(r -> r.getMember().getId().equals(memberId))
                .sorted(Comparator.comparing(Reservation::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    // Late fee checking service
    private void startLateFeeCheckService() {
        Thread lateFeeThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(3600000); // Check every hour
                    checkAndNotifyLateReturns();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        lateFeeThread.setDaemon(true);
        lateFeeThread.start();
    }

    private void checkAndNotifyLateReturns() {
        LocalDateTime now = LocalDateTime.now();
        for (Reservation reservation : reservationMap.values()) {
            if (reservation.getReservationStatus() == ReservationStatus.CONFIRMED 
                    && now.isAfter(reservation.getEndDate())) {
                notificationService.sendNotification(reservation.getMember(), NotificationType.LATE_RETURN,
                        "Your vehicle return is overdue. Please return the vehicle to avoid additional late fees.");
            }
        }
    }

    // Admin Operations
    public void addAdmin(Admin admin) {
        if (admin != null) {
            adminList.add(admin);
        }
    }

    // Equipment & Service Management
    public void addEquipment(Equipment equipment) {
        if (equipment != null) {
            equipmentList.add(equipment);
        }
    }

    public void addService(Service service) {
        if (service != null) {
            serviceList.add(service);
        }
    }

    // Getters
    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicleList);
    }

    public List<Member> getMembers() {
        return new ArrayList<>(memberList);
    }

    public List<Equipment> getAvailableEquipment() {
        return equipmentList.stream()
                .filter(eq -> eq.getEquipmentStatus() == EquipmentStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    public List<Service> getAvailableServices() {
        return serviceList.stream()
                .filter(svc -> svc.getServiceStatus() == ServiceStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    // Driver Management
    public void addDriver(Driver driver) {
        if (driver != null) {
            driverList.add(driver);
        }
    }

    public List<Driver> getAvailableDrivers() {
        return driverList.stream()
                .filter(Driver::isAvailable)
                .collect(Collectors.toList());
    }

    // Parking Stall Management
    public void addParkingStall(ParkingStall stall) {
        if (stall != null) {
            parkingStallList.add(stall);
        }
    }

    public ParkingStall getParkingStall(String stallId) {
        return parkingStallList.stream()
                .filter(s -> s.getId().equals(stallId))
                .findFirst()
                .orElse(null);
    }
}
