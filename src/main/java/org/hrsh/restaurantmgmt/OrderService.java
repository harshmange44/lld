package org.hrsh.restaurantmgmt;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderService {
    private static final double TAX_RATE = 0.10; // 10% tax
    private static final double SERVICE_CHARGE_RATE = 0.05; // 5% service charge

    public Order createOrder(Restaurant restaurant, Reservation reservation, List<MenuItem> menuItems) {
        if (restaurant == null || reservation == null || menuItems == null || menuItems.isEmpty()) {
            throw new IllegalArgumentException("Invalid parameters for order creation");
        }

        // Calculate total amount
        double subtotal = calculateSubtotal(menuItems);
        double taxAmount = subtotal * TAX_RATE;
        double serviceCharge = subtotal * SERVICE_CHARGE_RATE;
        double totalAmount = subtotal + taxAmount + serviceCharge;

        // Create order
        Order order = new Order();
        order.setId(UUID.randomUUID().toString());
        order.setItems(menuItems);
        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setReservation(reservation);
        order.setCustomerName(reservation.getCustomerName());

        return order;
    }

    public boolean isMenuItemAvailable(Restaurant restaurant, MenuItem menuItem) {
        if (restaurant == null || menuItem == null) {
            return false;
        }

        Menu menu = restaurant.getMenu();
        if (menu == null) {
            return false;
        }

        List<MenuItem> menuItems = menu.getMenuItems();
        if (menuItems == null || menuItems.isEmpty()) {
            return false;
        }

        // Check if menu item exists and is available
        return menuItems.stream()
                .anyMatch(item -> item.getId().equals(menuItem.getId()) && item.isAvailable());
    }

    private double calculateSubtotal(List<MenuItem> menuItems) {
        return menuItems.stream()
                .mapToDouble(MenuItem::getPrice)
                .sum();
    }
}

