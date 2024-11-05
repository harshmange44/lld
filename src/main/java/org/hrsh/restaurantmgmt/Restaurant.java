package org.hrsh.restaurantmgmt;

import java.util.*;

public class Restaurant {
    private String id;
    private String name;
    private Location location;
    private Menu menu;
    private List<Staff> staffList;
    private Map<String, Reservation> reservationsMap;
    private Map<String, Order> ordersMap;
    private final PaymentService paymentService;

    public Restaurant(String name, Location location) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.menu = new Menu();
        this.staffList = new ArrayList<>();
        this.reservationsMap = new HashMap<>();
        this.ordersMap = new HashMap<>();
        this.paymentService = new PaymentService();
    }

    public void placeOrder(Order order) {
        ordersMap.put(order.getId(), order);
        notifyKitchen();
    }

    private void notifyKitchen() {

    }

    public boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = ordersMap.get(orderId);
        if (order != null) {
            order.setOrderStatus(orderStatus);
            notifyStaff();
            return true;
        }
        return false;
    }

    private void notifyStaff() {

    }

    public void processReservation(Reservation reservation) {
        reservationsMap.put(reservation.getId(), reservation);
    }

    public void cancelReservation(String reservationId) {
        Reservation reservation = reservationsMap.get(reservationId);

        if (reservation != null) {
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservationsMap.put(reservationId, reservation);
        }
    }

    public PaymentReceipt processPayment(Order order, PaymentMethod paymentMethod) {
        BillReceipt billReceipt = new BillReceipt(order.getTotalAmount(), new ArrayList<>());
        return paymentService.process(billReceipt, paymentMethod);
    }
}
