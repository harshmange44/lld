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
        if (card == null || pin == null) {
            return false;
        }
        
        // Check card expiry
        if (card.getExpireAt() != null && card.getExpireAt().isBefore(java.time.LocalDateTime.now())) {
            return false;
        }
        
        return pin.equals(card.getPin());
    }

    public double checkBalance(String accountNumber) {
        Account account = bankingService.getAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }
        return account.getBalance();
    }

    /**
     * Withdraw cash with rollback support
     */
    public TransactionReceipt withdrawCash(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account account = bankingService.getAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        Transaction transaction = new WithdrawalTransaction(TransactionType.WITHDRAW, amount, account);
        
        try {
            // Phase 1: Reserve cash in ATM
            if (!cashDispenser.reserveCash(amount)) {
                transaction.setTransactionStatus(TransactionStatus.REJECTED);
                return generateReceipt(transaction, account, amount);
            }

            // Phase 2: Execute transaction
            bankingService.processTransaction(transaction);
            
            // Phase 3: Dispense cash
            TransactionReceipt receipt = cashDispenser.dispenseCash(transaction, amount);
            transactionCnt.getAndIncrement();
            return receipt;

        } catch (IllegalArgumentException e) {
            // Transaction was rejected
            cashDispenser.releaseReservation(amount);
            transaction.setTransactionStatus(TransactionStatus.REJECTED);
            return generateReceipt(transaction, account, amount);
            
        } catch (Exception e) {
            // Any other error - rollback transaction
            if (transaction.isExecuted()) {
                transaction.rollback();
            } else {
                cashDispenser.releaseReservation(amount);
            }
            transaction.setTransactionStatus(TransactionStatus.CANCELLED);
            return generateReceipt(transaction, account, amount);
        }
    }

    /**
     * Deposit cash with rollback support
     */
    public TransactionReceipt depositCash(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        Account account = bankingService.getAccount(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        Transaction transaction = new DepositTransaction(TransactionType.DEPOSIT, amount, account);
        
        try {
            // Execute transaction
            bankingService.processTransaction(transaction);
            
            cashDispenser.credit(amount);
            transactionCnt.getAndIncrement();
            
            return generateReceipt(transaction, account, amount);

        } catch (Exception e) {
            // Rollback on any error
            if (transaction.isExecuted()) {
                transaction.rollback();
            }
            transaction.setTransactionStatus(TransactionStatus.CANCELLED);
            return generateReceipt(transaction, account, amount);
        }
    }

    /**
     * Get account by card number
     */
    public Account getAccountByCard(String cardNumber) {
        return bankingService.getAccountByCard(cardNumber);
    }

    /**
     * Link card to account
     * One account can have multiple cards
     */
    public boolean linkCardToAccount(String cardNumber, String accountNumber) {
        return bankingService.linkCardToAccount(cardNumber, accountNumber);
    }

    /**
     * Unlink a card from its account
     */
    public boolean unlinkCard(String cardNumber) {
        return bankingService.unlinkCard(cardNumber);
    }

    /**
     * Get all cards linked to an account
     */
    public List<String> getCardsForAccount(String accountNumber) {
        return bankingService.getCardsForAccount(accountNumber);
    }

    /**
     * Check if a card is linked to an account
     */
    public boolean isCardLinkedToAccount(String cardNumber, String accountNumber) {
        return bankingService.isCardLinkedToAccount(cardNumber, accountNumber);
    }

    /**
     * Get count of cards for an account
     */
    public int getCardCountForAccount(String accountNumber) {
        return bankingService.getCardCountForAccount(accountNumber);
    }

    /**
     * Helper method to generate receipt
     */
    private TransactionReceipt generateReceipt(Transaction transaction, Account account, double amount) {
        return new TransactionReceipt(
                transaction.getId(),
                account.getNumber(),
                account.getName(),
                amount,
                transaction.getTransactionType(),
                transaction.getTransactionStatus()
        );
    }

    public int getTransactionCount() {
        return transactionCnt.get();
    }
}
