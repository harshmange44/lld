package org.hrsh.stockbrokeragesystem;

public class BuyOrder extends Order {
    public BuyOrder(Account account, Stock stock, int quantity, double totalPrice) {
        super(account, stock, quantity, totalPrice);
    }

    @Override
    public boolean execute() {
        Account account = getAccount();
        if (getTotalPrice() > account.getBalance()) {
            setOrderStatus(OrderStatus.CANCELLED);
            throw new InsufficientFundsException(String.format("Insufficient funds in the Account: %s", getTotalPrice() - account.getBalance()));
        }

        account.debit(getTotalPrice());
        Portfolio portfolio = account.getPortfolio();

        try {
            portfolio.addStock(getStock().getId(), getQuantity());
            setOrderStatus(OrderStatus.EXECUTED);
        } catch (Exception e) {
            setOrderStatus(OrderStatus.CANCELLED);
            e.printStackTrace();
        }
        return true;
    }
}
