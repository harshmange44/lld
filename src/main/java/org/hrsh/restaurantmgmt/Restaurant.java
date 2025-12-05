package org.hrsh.restaurantmgmt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Restaurant {
    private String id;
    private String name;
    private Location location;
    private Menu menu;
    private List<Staff> staffList;
    private List<Table> tables; // Added table management
    private Map<String, Reservation> reservationsMap;
    private Map<String, Order> ordersMap;
    private final PaymentService paymentService;

    public Restaurant(String name, Location location) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.menu = new Menu();
        this.staffList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.tables = new CopyOnWriteArrayList<>(); // Thread-safe
        this.reservationsMap = new ConcurrentHashMap<>();
        this.ordersMap = new ConcurrentHashMap<>();
        this.paymentService = new PaymentService();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Staff> getStaffList() {
        return new ArrayList<>(staffList); // Return defensive copy
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList != null ? new CopyOnWriteArrayList<>(staffList) : new CopyOnWriteArrayList<>();
    }

    public List<Table> getTables() {
        return new ArrayList<>(tables); // Return defensive copy
    }

    public void setTables(List<Table> tables) {
        this.tables = tables != null ? new CopyOnWriteArrayList<>(tables) : new CopyOnWriteArrayList<>();
    }

    public void addTable(Table table) {
        if (table != null) {
            tables.add(table);
        }
    }

    public synchronized void placeOrder(Order order) {
        if (order != null) {
            ordersMap.put(order.getId(), order);
            notifyKitchen(order);
        }
    }

    private void notifyKitchen(Order order) {
        // Notification handled by NotificationService
        // This method can be extended for direct kitchen system integration
    }

    public synchronized boolean updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = ordersMap.get(orderId);
        if (order != null) {
            order.setOrderStatus(orderStatus);
            notifyStaff(order);
            return true;
        }
        return false;
    }

    private void notifyStaff(Order order) {
        // Notification handled by NotificationService
        // This method can be extended for direct staff notification system
    }

    public synchronized void processReservation(Reservation reservation) {
        if (reservation != null) {
            reservationsMap.put(reservation.getId(), reservation);
        }
    }

    public synchronized void cancelReservation(String reservationId) {
        Reservation reservation = reservationsMap.get(reservationId);
        if (reservation != null) {
            reservation.setReservationStatus(ReservationStatus.CANCELLED);
            reservationsMap.put(reservationId, reservation);
        }
    }

    public PaymentReceipt processPayment(Order order, PaymentMethod paymentMethod) {
        if (order == null || paymentMethod == null) {
            throw new IllegalArgumentException("Order and PaymentMethod cannot be null");
        }

        BillReceipt billReceipt = new BillReceipt(order.getTotalAmount(), new ArrayList<>());
        return paymentService.process(billReceipt, paymentMethod);
    }

    public List<Order> getActiveOrders() {
        return new ArrayList<>(ordersMap.values());
    }
}
