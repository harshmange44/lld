package org.hrsh.stockbrokeragesystem;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class StockBrokerageSystem {
    /**
     * 1. Multiple Accounts/Users
     * 2. Multiple Stocks
     * 3. Multiple Orders
     * 4. Each Account has exactly one Portfolio
     * 5. Two types of Order - Buy or Sell
     * 6. Thread safe Transaction with an Account
    */

    Map<String, Account> accountMap;
    Map<String, Stock> stockMap;
    Queue<Order> orderQueue;

    public StockBrokerageSystem() {
        this.accountMap = new ConcurrentHashMap<>();
        this.stockMap = new ConcurrentHashMap<>();
        this.orderQueue = new ConcurrentLinkedDeque<>();

        runOrderExecution();
    }

    private void runOrderExecution() {
        new Thread(() -> {
            while (true) {
                while (orderQueue.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Order order = orderQueue.poll();
                order.execute();
            }
        }).start();
    }

    public Account createAccount(Account account) {
        return accountMap.put(account.getId(), account);
    }

    public Stock addStock(Stock stock) {
        return stockMap.put(stock.getId(), stock);
    }

    public boolean placeOrder(Order order) {
        return orderQueue.offer(order);
    }
}
