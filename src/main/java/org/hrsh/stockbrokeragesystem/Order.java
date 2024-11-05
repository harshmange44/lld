package org.hrsh.stockbrokeragesystem;

import java.util.UUID;

public abstract class Order {
    private final String id;
    private final Account account;
    private final Stock stock;
    private int quantity;
    private double totalPrice;
    private OrderStatus orderStatus;

    public Order(Account account, Stock stock, int quantity, double totalPrice) {
        this.id = UUID.randomUUID().toString();
        this.account = account;
        this.stock = stock;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderStatus = OrderStatus.PENDING;
    }

    public abstract boolean execute();

    public String getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public Stock getStock() {
        return stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
