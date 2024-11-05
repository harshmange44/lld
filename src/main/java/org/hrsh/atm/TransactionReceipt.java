package org.hrsh.atm;

public class TransactionReceipt {
    private String transactionId;
    private String accountNumber;
    private String accountName;
    private double amount;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;

    public TransactionReceipt(String transactionId, String accountNumber, String accountName,
                              double amount, TransactionType transactionType,
                              TransactionStatus transactionStatus) {
        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionStatus = transactionStatus;
    }
}
