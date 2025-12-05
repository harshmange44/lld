package org.hrsh.stockbrokeragesystem;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Portfolio {
    private final String id;
    private final Map<String, Integer> holdings;

    public Portfolio() {
        this.id = UUID.randomUUID().toString();
        this.holdings = new ConcurrentHashMap<>();
    }

    public String getId() {
        return id;
    }

    public synchronized void addStock(String stockId, int quantity) {
        if (stockId == null || quantity <= 0) {
            throw new IllegalArgumentException("Stock ID cannot be null and quantity must be positive");
        }
        holdings.put(stockId, holdings.getOrDefault(stockId, 0) + quantity);
    }

    public synchronized void removeStock(String stockId, int quantity) {
        if (stockId == null || quantity <= 0) {
            throw new IllegalArgumentException("Stock ID cannot be null and quantity must be positive");
        }
        
        if (!holdings.containsKey(stockId)) {
            throw new InsufficientStocksException("Insufficient stock quantity in the Holding: 0");
        }
        
        int currentQuantity = holdings.get(stockId);

        if (currentQuantity < quantity) {
            throw new InsufficientStocksException(
                    String.format("Insufficient stock quantity. Required: %d, Available: %d", 
                            quantity, currentQuantity));
        } else if (currentQuantity == quantity) {
            holdings.remove(stockId);
        } else {
            holdings.put(stockId, currentQuantity - quantity);
        }
    }

    public Map<String, Integer> getHoldings() {
        // Return defensive copy
        return new HashMap<>(holdings);
    }

    public int getStockQuantity(String stockId) {
        return holdings.getOrDefault(stockId, 0);
    }
}
