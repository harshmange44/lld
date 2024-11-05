package org.hrsh.stockbrokeragesystem;

public class Account {
    private String id;
    private User user;
    private Portfolio portfolio;
    private double balance;

    public Account(User user, Portfolio portfolio, double balance) {
        this.user = user;
        this.portfolio = portfolio;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public synchronized void debit(double amount) {
        balance -= amount;
    }

    public synchronized void credit(double amount) {
        balance += amount;
    }
}
