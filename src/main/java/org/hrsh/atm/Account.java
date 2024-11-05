package org.hrsh.atm;

public class Account {
    private String id;
    private String name;
    private String number;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
