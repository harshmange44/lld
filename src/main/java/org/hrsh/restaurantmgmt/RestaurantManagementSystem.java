package org.hrsh.restaurantmgmt;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class RestaurantManagementSystem {
    /**
     * Main orchestrator for Restaurant Management System
     * Handles: Reservations, Orders, Table Management, Payments
     */

    private final List<Restaurant> restaurantList;
    private final Map<String, Reservation> activeReservations; // reservationId -> Reservation
    private final Map<String, Order> activeOrders; // orderId -> Order
    private final ReservationService reservationService;
    private final OrderService orderService;
    private final NotificationService notificationService;

    public RestaurantManagementSystem() {
        this.restaurantList = new CopyOnWriteArrayList<>(); // Thread-safe
        this.activeReservations = new ConcurrentHashMap<>();
        this.activeOrders = new ConcurrentHashMap<>();
        this.reservationService = new ReservationService();
        this.orderService = new OrderService();
        this.notificationService = new NotificationService();
    }

    // Restaurant Management
    public void addRestaurant(Restaurant restaurant) {
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        restaurantList.add(restaurant);
    }

    public Restaurant getRestaurant(String restaurantId) {
        return restaurantList.stream()
                .filter(r -> r.getId().equals(restaurantId))
                .findFirst()
                .orElse(null);
    }

    // Reservation Management
    public synchronized Reservation createReservation(String restaurantId, String customerName, 
                                                     String customerPhone, int totalPeople, 
                                                     LocalDateTime reservationDate) {
        if (restaurantId == null || customerName == null || customerPhone == null || reservationDate == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        if (totalPeople <= 0) {
            throw new IllegalArgumentException("Total people must be greater than 0");
        }

        if (reservationDate.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Reservation date cannot be in the past");
        }

        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        // Check table availability
        Table table = reservationService.findAvailableTable(restaurant, totalPeople, reservationDate);
        if (table == null) {
            throw new IllegalStateException("No available table for " + totalPeople + " people at this time");
        }

        // Create reservation
        Reservation reservation = reservationService.createReservation(
                restaurant, customerName, customerPhone, totalPeople, reservationDate, table);

        // Store active reservation
        activeReservations.put(reservation.getId(), reservation);
        restaurant.processReservation(reservation);

        // Send notification
        notificationService.sendNotification(customerName, NotificationType.RESERVATION_CONFIRMED,
                "Reservation confirmed at " + restaurant.getName() + " for " + totalPeople + " people on " 
                + reservationDate.toLocalDate());

        return reservation;
    }

    public boolean cancelReservation(String restaurantId, String reservationId) {
        if (restaurantId == null || reservationId == null) {
            throw new IllegalArgumentException("Restaurant ID and Reservation ID are required");
        }

        Reservation reservation = activeReservations.get(reservationId);
        if (reservation == null) {
            return false;
        }

        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant != null) {
            reservationService.cancelReservation(reservation);
            restaurant.cancelReservation(reservationId);
        }

        activeReservations.remove(reservationId);

        // Send notification
        notificationService.sendNotification(reservation.getCustomerName(), NotificationType.RESERVATION_CANCELLED,
                "Reservation cancelled successfully");

        return true;
    }

    // Order Management
    public synchronized Order placeOrder(String restaurantId, String reservationId, List<MenuItem> menuItems) {
        if (restaurantId == null || reservationId == null || menuItems == null || menuItems.isEmpty()) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        Reservation reservation = activeReservations.get(reservationId);
        if (reservation == null || reservation.getReservationStatus() != ReservationStatus.RESERVED) {
            throw new IllegalStateException("Reservation not found or not confirmed");
        }

        // Validate menu items are available
        for (MenuItem item : menuItems) {
            if (!orderService.isMenuItemAvailable(restaurant, item)) {
                throw new IllegalStateException("MenuItem " + item.getName() + " is not available");
            }
        }

        // Create order
        Order order = orderService.createOrder(restaurant, reservation, menuItems);

        // Store active order
        activeOrders.put(order.getId(), order);
        restaurant.placeOrder(order);

        // Send notification to kitchen
        notificationService.sendNotification("Kitchen", NotificationType.ORDER_PLACED,
                "New order received: " + order.getId() + " with " + menuItems.size() + " items");

        return order;
    }

    public synchronized boolean updateOrderStatus(String restaurantId, String orderId, OrderStatus newStatus) {
        if (restaurantId == null || orderId == null || newStatus == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Order order = activeOrders.get(orderId);
        if (order == null) {
            return false;
        }

        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant != null) {
            boolean updated = restaurant.updateOrderStatus(orderId, newStatus);
            if (updated) {
                // Send notification based on status
                NotificationType notificationType = switch (newStatus) {
                    case READY -> NotificationType.ORDER_READY;
                    case COMPLETED -> NotificationType.ORDER_COMPLETED;
                    case CANCELLED -> NotificationType.ORDER_CANCELLED;
                    default -> NotificationType.OTHER;
                };

                notificationService.sendNotification(order.getCustomerName(), notificationType,
                        "Order " + orderId + " status updated to: " + newStatus);
            }
            return updated;
        }

        return false;
    }

    // Payment Processing
    public PaymentReceipt processPayment(String restaurantId, String orderId, PaymentMethod paymentMethod) {
        if (restaurantId == null || orderId == null || paymentMethod == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Order order = activeOrders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getOrderStatus() != OrderStatus.COMPLETED) {
            throw new IllegalStateException("Order must be completed before payment");
        }

        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        PaymentReceipt receipt = restaurant.processPayment(order, paymentMethod);

        // Send notification
        notificationService.sendNotification(order.getCustomerName(), NotificationType.PAYMENT_SUCCESS,
                "Payment successful. Amount: $" + receipt.getAmountPayed());

        return receipt;
    }

    // Getters
    public List<Restaurant> getRestaurants() {
        return new ArrayList<>(restaurantList);
    }

    public Reservation getReservation(String reservationId) {
        return activeReservations.get(reservationId);
    }

    public Order getOrder(String orderId) {
        return activeOrders.get(orderId);
    }

    public List<Order> getRestaurantOrders(String restaurantId) {
        Restaurant restaurant = getRestaurant(restaurantId);
        if (restaurant == null) {
            return Collections.emptyList();
        }
        return restaurant.getActiveOrders();
    }
}
