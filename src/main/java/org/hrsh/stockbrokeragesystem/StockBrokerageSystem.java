package org.hrsh.stockbrokeragesystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StockBrokerageSystem {
    /**
     * Main orchestrator for Stock Brokerage System
     * Handles: Account Management, Stock Management, Order Placement & Execution
     */

    private static final String ORDER_PREFIX = "Order ";

    private final Map<String, Account> accountMap;
    private final Map<String, Stock> stockMap;
    private final Queue<Order> orderQueue;
    private final Map<String, Order> orderHistory; // orderId -> Order
    private final OrderExecutionService orderExecutionService;
    private final NotificationService notificationService;
    private final Object queueLock = new Object(); // Lock for order queue synchronization

    public StockBrokerageSystem() {
        this.accountMap = new ConcurrentHashMap<>();
        this.stockMap = new ConcurrentHashMap<>();
        this.orderQueue = new ConcurrentLinkedQueue<>();
        this.orderHistory = new ConcurrentHashMap<>();
        this.orderExecutionService = new OrderExecutionService();
        this.notificationService = new NotificationService();
        
        // Start order execution thread
        startOrderExecutionThread();
    }

    // Account Management
    public Account createAccount(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accountMap.put(account.getId(), account);
        return account;
    }

    public Account getAccount(String accountId) {
        return accountMap.get(accountId);
    }

    // Stock Management
    public Stock addStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("Stock cannot be null");
        }
        stockMap.put(stock.getId(), stock);
        return stock;
    }

    public Stock getStock(String stockId) {
        return stockMap.get(stockId);
    }

    public void updateStockPrice(String stockId, double newPrice) {
        Stock stock = stockMap.get(stockId);
        if (stock != null) {
            stock.setPrice(newPrice);
            // Notify users about price update
            notificationService.notifyStockPriceUpdate(stock, newPrice);
        }
    }

    // Order Management
    public synchronized boolean placeOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if (order.getAccount() == null || order.getStock() == null) {
            throw new IllegalArgumentException("Order must have valid account and stock");
        }

        // Validate account exists
        if (!accountMap.containsKey(order.getAccount().getId())) {
            throw new IllegalArgumentException("Account not found");
        }

        // Validate stock exists
        if (!stockMap.containsKey(order.getStock().getId())) {
            throw new IllegalArgumentException("Stock not found");
        }

        // Validate quantity
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException("Order quantity must be greater than 0");
        }

        // Validate price
        if (order.getTotalPrice() <= 0) {
            throw new IllegalArgumentException("Order price must be greater than 0");
        }

        // Add to order history
        orderHistory.put(order.getId(), order);

        // Add to execution queue
        boolean added = orderQueue.offer(order);

        if (added) {
            synchronized (queueLock) {
                queueLock.notifyAll(); // Notify execution thread
            }
            
            // Send notification
            notificationService.sendNotification(order.getAccount().getUser(), NotificationType.ORDER_PLACED,
                    ORDER_PREFIX + order.getId() + " placed for " + order.getQuantity() + " shares of " 
                    + order.getStock().getName());
        }

        return added;
    }

    public Order getOrder(String orderId) {
        return orderHistory.get(orderId);
    }

    public List<Order> getAccountOrders(String accountId) {
        return orderHistory.values().stream()
                .filter(order -> order.getAccount().getId().equals(accountId))
                .sorted(Comparator.comparing(Order::getCreatedAt).reversed())
                .toList();
    }

    // Order execution thread
    private void startOrderExecutionThread() {
        Thread executionThread = new Thread(() -> {
            while (true) {
                try {
                    Order order = null;
                    
                    synchronized (queueLock) {
                        while (orderQueue.isEmpty()) {
                            queueLock.wait(); // Wait for orders
                        }
                        order = orderQueue.poll();
                    }

                    if (order != null) {
                        executeOrder(order);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    // Log error and continue processing other orders
                    e.printStackTrace();
                }
            }
        });
        executionThread.setDaemon(true);
        executionThread.start();
    }

    private void executeOrder(Order order) {
        try {
            boolean executed = orderExecutionService.executeOrder(order, accountMap, stockMap);
            
            if (executed) {
                notificationService.sendNotification(order.getAccount().getUser(), NotificationType.ORDER_EXECUTED,
                        ORDER_PREFIX + order.getId() + " executed successfully. " + order.getQuantity() + " shares of " 
                        + order.getStock().getName());
            } else {
                notificationService.sendNotification(order.getAccount().getUser(), NotificationType.ORDER_FAILED,
                        ORDER_PREFIX + order.getId() + " failed to execute. Reason: Insufficient funds or stocks.");
            }
        } catch (Exception e) {
            order.setOrderStatus(OrderStatus.CANCELLED);
            notificationService.sendNotification(order.getAccount().getUser(), NotificationType.ORDER_FAILED,
                    ORDER_PREFIX + order.getId() + " cancelled due to error: " + e.getMessage());
        }
    }

    // Getters
    public Map<String, Account> getAccounts() {
        return new HashMap<>(accountMap);
    }

    public Map<String, Stock> getStocks() {
        return new HashMap<>(stockMap);
    }
}
