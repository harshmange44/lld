package org.hrsh.atm;

import java.util.UUID;

public abstract class Transaction {
    private String id;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private double amount;
    private Account account;

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

    public abstract void execute();
}
