package org.hrsh.restaurantmgmt;

import java.util.List;

public class Order {
    private String id;
    private List<MenuItem> items;
    private double totalAmount;
    private OrderStatus orderStatus;

    public String getId() {
        return id;
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
}
