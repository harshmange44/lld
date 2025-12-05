package org.hrsh.parkinglotmgmt;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ParkingLotSystem {
    // 1. Multiple Parking Lots
    // 2. Parking -> Multiple Floors
    // 3. Multiple Entry & Exit Points
    // 4. Entry Point - Collect Entry Point, Exit Point - Pay at Exit Point
    // 5. Customer Info Portal on each floor - May Pay here as well
    // 6. Payed via Cash, Card, etc.
    // 7. Display Panel at Entry Points
    // 8. Different types of Parking Spot - Compact, Large, etc.
    // 9. Parking Spot should support different types of vehicles - car, truck, etc.
    // 10. Parking Spot for Electric Car - Electric Panel
    // 11. Display Panel on each floor showing Available Parking Spots
    // 12. Per hour parking fee model

    private final List<ParkingLot> parkingLotList;
    private final Map<String, ParkingTicket> activeTickets; // ticketId -> ParkingTicket
    private final Map<String, Spot> vehicleSpotMap; // vehicleNumber -> Spot
    private final SpotAllocationService spotAllocationService;
    private final BillingService billingService;
    private final NotificationService notificationService;

    public ParkingLotSystem() {
        this.parkingLotList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.activeTickets = new ConcurrentHashMap<>();
        this.vehicleSpotMap = new ConcurrentHashMap<>();
        this.spotAllocationService = new SpotAllocationService();
        this.billingService = new BillingService();
        this.notificationService = new NotificationService();
        
        // Start display update service
        startDisplayUpdateService();
    }

    // Parking Lot Management
    public void addParkingLot(ParkingLot parkingLot) {
        if (parkingLot == null) {
            throw new IllegalArgumentException("Parking lot cannot be null");
        }
        parkingLotList.add(parkingLot);
    }

    public ParkingLot getParkingLot(String parkingLotId) {
        return parkingLotList.stream()
                .filter(lot -> lot.getId().equals(parkingLotId))
                .findFirst()
                .orElse(null);
    }

    // Entry Point Operations
    public synchronized ParkingTicket enterParkingLot(String parkingLotId, String entryPointId, Vehicle vehicle, String customerName) {
        if (parkingLotId == null || entryPointId == null || vehicle == null || customerName == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        ParkingLot parkingLot = getParkingLot(parkingLotId);
        if (parkingLot == null) {
            throw new IllegalArgumentException("Parking lot not found");
        }

        // Check if vehicle is already parked
        if (vehicleSpotMap.containsKey(vehicle.getNumber())) {
            throw new IllegalStateException("Vehicle is already parked in the lot");
        }

        // Find entry point
        EntryPoint entryPoint = parkingLot.getEntryPoints().stream()
                .filter(ep -> ep.getId().equals(entryPointId))
                .findFirst()
                .orElse(null);

        if (entryPoint == null) {
            throw new IllegalArgumentException("Entry point not found");
        }

        // Find available spot
        Spot spot = spotAllocationService.findAvailableSpot(parkingLot, vehicle);
        if (spot == null) {
            entryPoint.showTotalAvailableSpots(parkingLot.getFloors());
            throw new IllegalStateException("No available spots for this vehicle type");
        }

        // Assign vehicle to spot
        if (!spot.assignVehicle(vehicle)) {
            throw new IllegalStateException("Failed to assign vehicle to spot");
        }

        // Generate parking ticket
        ParkingTicket ticket = entryPoint.generateParkingTicket(vehicle, customerName);
        ticket.setSpot(spot);
        ticket.setEntryPoint(entryPoint);

        // Store active ticket and vehicle mapping
        activeTickets.put(ticket.getId(), ticket);
        vehicleSpotMap.put(vehicle.getNumber(), spot);

        // Update available spots count
        updateAvailableSpotsCount(parkingLot);

        // Update display panels
        updateDisplayPanels(parkingLot);

        // Send notification
        notificationService.sendNotification(customerName, NotificationType.ENTRY,
                "Vehicle " + vehicle.getNumber() + " parked at spot " + spot.getId());

        return ticket;
    }

    // Exit Point Operations
    public synchronized PaymentReceipt exitParkingLot(String parkingLotId, String exitPointId, String ticketId, PaymentMethod paymentMethod, double additionalCharge) {
        if (parkingLotId == null || exitPointId == null || ticketId == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        ParkingTicket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Parking ticket not found or already used");
        }

        ParkingLot parkingLot = getParkingLot(parkingLotId);
        if (parkingLot == null) {
            throw new IllegalArgumentException("Parking lot not found");
        }

        Spot spot = ticket.getSpot();
        Vehicle vehicle = ticket.getVehicle();

        // Generate bill
        BillReceipt billReceipt = billingService.generateBill(spot, ticket, additionalCharge);

        // Process payment
        CustomerPortal customerPortal = findCustomerPortal(parkingLot, exitPointId);
        if (customerPortal == null) {
            throw new IllegalArgumentException("Customer portal not found at exit point");
        }

        PaymentReceipt paymentReceipt = customerPortal.processPayment(billReceipt, paymentMethod);
        
        if (paymentReceipt.getPaymentStatus() != PaymentStatus.SUCCESSFUL) {
            throw new IllegalStateException("Payment failed. Cannot exit parking lot");
        }

        // Free the spot
        spot.freeVehicle();
        
        // Remove from active tickets and vehicle mapping
        activeTickets.remove(ticketId);
        vehicleSpotMap.remove(vehicle.getNumber());

        // Update available spots count
        updateAvailableSpotsCount(parkingLot);

        // Update display panels
        updateDisplayPanels(parkingLot);

        // Send notification
        notificationService.sendNotification(ticket.getCustomerName(), NotificationType.EXIT,
                "Vehicle " + vehicle.getNumber() + " exited. Amount paid: $" + billReceipt.getTotalAmount());

        return paymentReceipt;
    }

    // Get available spots count
    public int getAvailableSpotsCount(String parkingLotId) {
        ParkingLot parkingLot = getParkingLot(parkingLotId);
        if (parkingLot == null) {
            return 0;
        }
        return parkingLot.getFloors().stream()
                .mapToInt(Floor::getAvailableSpots)
                .sum();
    }

    // Private helper methods

    private void updateAvailableSpotsCount(ParkingLot parkingLot) {
        for (Floor floor : parkingLot.getFloors()) {
            long availableCount = floor.getSpots().stream()
                    .filter(spot -> spot.getSpotStatus() == SpotStatus.AVAILABLE)
                    .count();
            floor.setAvailableSpots((int) availableCount);
            floor.setAvailableSpots(); // Update display panels
        }
    }

    private void updateDisplayPanels(ParkingLot parkingLot) {
        int totalAvailable = getAvailableSpotsCount(parkingLot.getId());
        
        // Update entry point displays
        for (EntryPoint entryPoint : parkingLot.getEntryPoints()) {
            entryPoint.showTotalAvailableSpots(parkingLot.getFloors());
        }
        
        // Update floor displays
        for (Floor floor : parkingLot.getFloors()) {
            floor.setAvailableSpots();
        }
    }

    private CustomerPortal findCustomerPortal(ParkingLot parkingLot, String exitPointId) {
        // Find customer portal at exit point or on any floor
        for (Floor floor : parkingLot.getFloors()) {
            if (floor.getCustomerPortals() != null) {
                for (CustomerPortal portal : floor.getCustomerPortals()) {
                    if (portal != null) {
                        return portal; // Return first available portal
                    }
                }
            }
        }
        return null;
    }

    private void startDisplayUpdateService() {
        Thread displayThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30000); // Update every 30 seconds
                    for (ParkingLot parkingLot : parkingLotList) {
                        updateDisplayPanels(parkingLot);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        displayThread.setDaemon(true);
        displayThread.start();
    }

    // Getters
    public List<ParkingLot> getParkingLotList() {
        return new ArrayList<>(parkingLotList);
    }

    public ParkingTicket getActiveTicket(String ticketId) {
        return activeTickets.get(ticketId);
    }
}
