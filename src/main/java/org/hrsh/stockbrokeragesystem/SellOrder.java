package org.hrsh.stockbrokeragesystem;

import java.time.LocalDateTime;

public class SellOrder extends Order {
    public SellOrder(Account account, Stock stock, int quantity, double totalPrice) {
        super(account, stock, quantity, totalPrice);
    }

    @Override
    public boolean execute() {
        setExecutedAt(LocalDateTime.now());
        Account account = getAccount();
        account.credit(getTotalPrice());
        Portfolio portfolio = account.getPortfolio();
        try {
            portfolio.removeStock(getStock().getId(), getQuantity());
            setOrderStatus(OrderStatus.EXECUTED);
        } catch (Exception e) {
            setOrderStatus(OrderStatus.CANCELLED);
            e.printStackTrace();
        }
        return true;
    }
}
