package org.hrsh.stockbrokeragesystem;

import java.time.LocalDateTime;
import java.util.Map;

public class OrderExecutionService {
    /**
     * Service for executing buy and sell orders with proper validation and rollback
     */

    public boolean executeOrder(Order order, Map<String, Account> accountMap, Map<String, Stock> stockMap) {
        if (order == null || accountMap == null || stockMap == null) {
            return false;
        }

        if (order instanceof BuyOrder) {
            return executeBuyOrder((BuyOrder) order, accountMap, stockMap);
        } else if (order instanceof SellOrder) {
            return executeSellOrder((SellOrder) order, accountMap, stockMap);
        }

        return false;
    }

    private boolean executeBuyOrder(BuyOrder order, Map<String, Account> accountMap, Map<String, Stock> stockMap) {
        Account account = order.getAccount();
        double requiredAmount = order.getTotalPrice();

        // Validate account has sufficient funds
        synchronized (account) {
            if (account.getBalance() < requiredAmount) {
                order.setOrderStatus(OrderStatus.CANCELLED);
                throw new InsufficientFundsException(
                        String.format("Insufficient funds. Required: %.2f, Available: %.2f", 
                                requiredAmount, account.getBalance()));
            }

            // Debit the amount
            account.debit(requiredAmount);

            try {
                // Add stock to portfolio
                Portfolio portfolio = account.getPortfolio();
                portfolio.addStock(order.getStock().getId(), order.getQuantity());

                // Mark order as executed
                order.setExecutedAt(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.EXECUTED);
                return true;
            } catch (Exception e) {
                // Rollback: credit back the amount
                account.credit(requiredAmount);
                order.setOrderStatus(OrderStatus.CANCELLED);
                throw new RuntimeException("Failed to add stock to portfolio: " + e.getMessage(), e);
            }
        }
    }

    private boolean executeSellOrder(SellOrder order, Map<String, Account> accountMap, Map<String, Stock> stockMap) {
        Account account = order.getAccount();
        Portfolio portfolio = account.getPortfolio();
        String stockId = order.getStock().getId();
        int quantityToSell = order.getQuantity();

        // Validate portfolio has sufficient stocks
        synchronized (portfolio) {
            int currentQuantity = portfolio.getHoldings().getOrDefault(stockId, 0);
            
            if (currentQuantity < quantityToSell) {
                order.setOrderStatus(OrderStatus.CANCELLED);
                throw new InsufficientStocksException(
                        String.format("Insufficient stocks. Required: %d, Available: %d", 
                                quantityToSell, currentQuantity));
            }

            try {
                // Remove stock from portfolio first
                portfolio.removeStock(stockId, quantityToSell);

                // Credit the amount
                synchronized (account) {
                    account.credit(order.getTotalPrice());
                }

                // Mark order as executed
                order.setExecutedAt(LocalDateTime.now());
                order.setOrderStatus(OrderStatus.EXECUTED);
                return true;
            } catch (Exception e) {
                // If credit fails, rollback by adding stock back
                try {
                    portfolio.addStock(stockId, quantityToSell);
                } catch (Exception rollbackException) {
                    // Log rollback failure
                    rollbackException.printStackTrace();
                }
                order.setOrderStatus(OrderStatus.CANCELLED);
                throw new RuntimeException("Failed to execute sell order: " + e.getMessage(), e);
            }
        }
    }
}

