package org.hrsh.atm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BankingService {
    private final Map<String, Account> accountMap = new HashMap<>();
    private final Map<String, String> cardToAccountMap = new HashMap<>();

    public boolean createAccount(Account account) {
        if (accountMap.containsKey(account.getNumber())) return false;

        accountMap.put(account.getNumber(), account);
        return true;
    }

    public Account getAccount(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    /**
     * Link a card to an account
     * One account can have multiple cards (one-to-many relationship)
     * If card is already linked to another account, it will be reassigned
     */
    public boolean linkCardToAccount(String cardNumber, String accountNumber) {
        if (!accountMap.containsKey(accountNumber)) {
            return false;
        }
        cardToAccountMap.put(cardNumber, accountNumber);
        return true;
    }

    /**
     * Unlink a card from its account
     */
    public boolean unlinkCard(String cardNumber) {
        return cardToAccountMap.remove(cardNumber) != null;
    }

    /**
     * Get account associated with a card
     */
    public Account getAccountByCard(String cardNumber) {
        String accountNumber = cardToAccountMap.get(cardNumber);
        if (accountNumber == null) {
            return null;
        }
        return accountMap.get(accountNumber);
    }

    /**
     * Get all cards linked to an account (one account → multiple cards)
     */
    public List<String> getCardsForAccount(String accountNumber) {
        if (!accountMap.containsKey(accountNumber)) {
            return new ArrayList<>();
        }
        
        return cardToAccountMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(accountNumber))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Check if a card is linked to an account
     */
    public boolean isCardLinkedToAccount(String cardNumber, String accountNumber) {
        String linkedAccount = cardToAccountMap.get(cardNumber);
        return linkedAccount != null && linkedAccount.equals(accountNumber);
    }

    /**
     * Get count of cards linked to an account
     */
    public int getCardCountForAccount(String accountNumber) {
        return getCardsForAccount(accountNumber).size();
    }

    public void processTransaction(Transaction transaction) {
        transaction.execute();
    }
}
