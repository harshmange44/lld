package org.hrsh.atm;

import java.util.UUID;

public abstract class Transaction {
    private String id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private double amount;
    private Account account;
    private boolean executed = false;

    public Transaction(TransactionType transactionType, double amount, Account account) {
        this.id = UUID.randomUUID().toString();
        this.transactionType = transactionType;
        this.transactionStatus = TransactionStatus.PENDING;
        this.amount = amount;
        this.account = account;
    }

    public String getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public abstract void execute();
    
    public void rollback() {
        if (!executed) {
            return;
        }
        
        if (transactionType == TransactionType.WITHDRAW) {
            account.credit(amount);
        } else if (transactionType == TransactionType.DEPOSIT) {
            account.debit(amount);
        }
        
        this.transactionStatus = TransactionStatus.CANCELLED;
    }
}
