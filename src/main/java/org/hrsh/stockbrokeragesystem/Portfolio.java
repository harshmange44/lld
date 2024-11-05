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

    public synchronized void addStock(String stockId, int quantity) {
        holdings.put(stockId, holdings.getOrDefault(stockId, 0) + quantity);
    }


    public synchronized void removeStock(String stockId, int quantity) {
        if (holdings.containsKey(stockId)) {
            int currentQuantity = holdings.get(stockId);

            if (currentQuantity > quantity) {
                holdings.put(stockId, currentQuantity - quantity);
            } else if (currentQuantity == quantity) {
                holdings.remove(stockId);
            } else {
                throw new InsufficientStocksException(String.format("Insufficient stock quantity in the Holding: %s", currentQuantity - quantity));
            }
        } else {
            throw new InsufficientStocksException("Insufficient stock quantity in the Holding: 0");
        }
    }

    public Map<String, Integer> getHoldings() {
        return holdings;
    }
}
