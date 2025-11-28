package org.hrsh.movieticketbookingsystem;

public class Account {
    private String email;
    private String password;
    private AccountStatus accountStatus;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.accountStatus = AccountStatus.ACTIVE;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }
}
