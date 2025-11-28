package org.hrsh.atm;

public class CashDispenser {
    private double availableCash;
    private double reservedCash = 0;

    public CashDispenser(double availableCash) {
        this.availableCash = availableCash;
    }

    /**
     * Reserve cash before processing transaction
     * Returns true if cash can be reserved, false otherwise
     */
    public synchronized boolean reserveCash(double amount) {
        if (amount > (availableCash - reservedCash)) {
            return false; // Not enough cash available
        }
        reservedCash += amount;
        return true;
    }

    /**
     * Release reserved cash (for rollback)
     */
    public synchronized void releaseReservation(double amount) {
        if (reservedCash >= amount) {
            reservedCash -= amount;
        }
    }

    /**
     * Dispense cash and clear reservation
     */
    public synchronized TransactionReceipt dispenseCash(Transaction transaction, double amount) {
        // Check if cash was reserved
        if (reservedCash < amount) {
            throw new IllegalStateException("Cash was not reserved for this transaction");
        }

        // Deduct from available and reserved cash
        availableCash -= amount;
        reservedCash -= amount;

        Account account = transaction.getAccount();
        return new TransactionReceipt(transaction.getId(),
                account.getNumber(), account.getName(), amount, TransactionType.WITHDRAW, 
                transaction.getTransactionStatus());
    }

    public synchronized void credit(double amount) {
        availableCash += amount;
    }

    public synchronized double getAvailableCash() {
        return availableCash - reservedCash;
    }

    public synchronized double getTotalCash() {
        return availableCash;
    }
}
