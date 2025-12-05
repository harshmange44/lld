package org.hrsh.restaurantmgmt;

import java.util.List;
import java.util.UUID;

public class Order {
    private String id;
    private List<MenuItem> items;
    private double totalAmount;
    private OrderStatus orderStatus;
    private Reservation reservation; // Added reservation reference
    private String customerName; // Added for notifications

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderStatus = OrderStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
