package org.hrsh.atm;

import java.util.HashMap;
import java.util.Map;

public class BankingService {
    private final Map<String, Account> accountMap = new HashMap<>();

    public boolean createAccount(Account account) {
        if (accountMap.containsKey(account.getNumber())) return false;

        accountMap.put(account.getNumber(), account);
        return true;
    }

    public Account getAccount(String accountNumber) {
        return accountMap.get(accountNumber);
    }

    public void processTransaction(Transaction transaction) {
        transaction.execute();
    }
}
