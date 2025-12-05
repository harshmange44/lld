package org.hrsh.stockbrokeragesystem;

public class BuyOrder extends Order {
    public BuyOrder(Account account, Stock stock, int quantity, double totalPrice) {
        super(account, stock, quantity, totalPrice);
    }

    @Override
    public boolean execute() {
        // Execution is now handled by OrderExecutionService
        // This method is kept for backward compatibility but should not be called directly
        throw new UnsupportedOperationException("Order execution should be handled by OrderExecutionService");
    }
}
