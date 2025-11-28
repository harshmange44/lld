package org.hrsh.atm;

public class WithdrawalTransaction extends Transaction {
    public WithdrawalTransaction(TransactionType transactionType, double amount, Account account) {
        super(transactionType, amount, account);
    }

    @Override
    public void execute() {
        if (getAmount() > getAccount().getBalance()) {
            setTransactionStatus(TransactionStatus.REJECTED);
            throw new IllegalArgumentException("Insufficient cash available in the Account...");
        }
        getAccount().debit(getAmount());
        setExecuted(true);
        setTransactionStatus(TransactionStatus.COMPLETED);
    }
}
