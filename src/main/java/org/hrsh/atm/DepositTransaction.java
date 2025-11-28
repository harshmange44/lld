package org.hrsh.atm;

public class DepositTransaction extends Transaction {
    public DepositTransaction(TransactionType transactionType, double amount, Account account) {
        super(transactionType, amount, account);
    }

    @Override
    public void execute() {
        getAccount().credit(getAmount());
        setExecuted(true);
        setTransactionStatus(TransactionStatus.COMPLETED);
    }
}
