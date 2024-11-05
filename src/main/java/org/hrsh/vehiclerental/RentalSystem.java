package org.hrsh.vehiclerental;

import org.hrsh.vehiclerental.equipment.Equipment;
import org.hrsh.vehiclerental.service.Driver;
import org.hrsh.vehiclerental.service.Service;
import org.hrsh.vehiclerental.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RentalSystem {
    /**
     * 1. Multiple Vehicles & Diff type of vehicles
     * 2. Admin can Add, Modify Vehicles
     * 3. Member can check out/reserve vehicles
     * 4. Member can add insurance to the reservation
     * 5. Member can rent additional equipments - navigation, child seat, etc
     * 6. Member can add additional services - assistance, driver, WI-FI, etc
     * 7. Late fees on passed due date returns
     * 8. Vehicle should have unique barcode
     * 9. Vehicle should have Parking Stall to easily locate
     * 10. System should notify
    */

    private final List<Vehicle> vehicleList;
    private final List<Member> memberList;
    private final List<Admin> adminList;
    private final List<Reservation> reservationList;
    private final List<Driver> driverList;
    private final List<ParkingStall> parkingStallList;
    private final List<Equipment> equipmentList;
    private final List<Service> serviceList;

    private final PaymentService paymentService;

    public RentalSystem() {
        this.paymentService = new PaymentService();
        this.vehicleList = new ArrayList<>();
        this.memberList = new ArrayList<>();
        this.adminList = new ArrayList<>();
        this.reservationList = new ArrayList<>();
        this.driverList = new ArrayList<>();
        this.parkingStallList = new ArrayList<>();
        this.equipmentList = new ArrayList<>();
        this.serviceList = new ArrayList<>();
    }

    public BillReceipt reserveVehicle(Member member, int noOfDays, Vehicle vehicle,
                                      List<Equipment> equipmentList, List<Service> serviceList) {
        double totalAmount = 0;
        totalAmount += vehicle.getPricePerDay()*noOfDays;

        Reservation reservation = new Reservation();
        reservation.setTotalPrice(totalAmount);

        reservationList.add(reservation);

        return new BillReceipt(reservation.getTotalPrice(), new ArrayList<>());
    }

    public PaymentReceipt processPayment(BillReceipt billReceipt, PaymentMethod paymentMethod) {
        return paymentService.process(billReceipt, paymentMethod);
    }

    public PaymentReceipt acceptVehicle(Member member, BillReceipt billReceipt, PaymentMethod paymentMethod) {
        return paymentService.process(billReceipt, paymentMethod);
    }
}