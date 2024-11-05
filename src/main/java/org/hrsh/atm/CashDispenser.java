package org.hrsh.atm;

public class CashDispenser {
    private double availableCash;

    public CashDispenser(double availableCash) {
        this.availableCash = availableCash;
    }

    public synchronized TransactionReceipt dispenseCash(Transaction transaction, double amount) {
        if (amount > availableCash) {
            throw new IllegalArgumentException("Insufficient cash available in the ATM...");
        }

        availableCash -= amount;

        Account account = transaction.getAccount();
        return new TransactionReceipt(transaction.getId(),
                account.getNumber(), account.getName(), amount, TransactionType.WITHDRAW, transaction.getTransactionStatus());
    }
}
