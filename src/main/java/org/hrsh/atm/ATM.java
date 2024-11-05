package org.hrsh.atm;

import java.util.concurrent.atomic.AtomicInteger;

public class ATM {
    private final BankingService bankingService;
    private final CashDispenser cashDispenser;
    private final AtomicInteger transactionCnt;

    public ATM() {
        this.bankingService = new BankingService();
        this.cashDispenser = new CashDispenser(Constants.DEFAULT_CASH_DISPENSER_CASH);
        this.transactionCnt = new AtomicInteger(0);
    }

    public boolean authenticateUser(Card card, String pin) {
        return pin.equals(card.getPin());
    }

    public double checkBalance(String accountNumber) {
        Account account = bankingService.getAccount(accountNumber);
        return account.getBalance();
    }

    public TransactionReceipt withdrawCash(String accountNumber, double amount) {
        Account account = bankingService.getAccount(accountNumber);
        Transaction transaction = new WithdrawalTransaction(TransactionType.WITHDRAW, amount, account);
        bankingService.processTransaction(transaction);
        transactionCnt.getAndIncrement();
        return cashDispenser.dispenseCash(transaction, amount);
    }

    public TransactionReceipt depositCash(String accountNumber, double amount) {
        Account account = bankingService.getAccount(accountNumber);
        Transaction transaction = new WithdrawalTransaction(TransactionType.DEPOSIT, amount, account);
        bankingService.processTransaction(transaction);
        transactionCnt.getAndIncrement();
        return new TransactionReceipt(transaction.getId(),
                account.getNumber(), account.getName(), amount, TransactionType.DEPOSIT,
                transaction.getTransactionStatus());
    }
}
