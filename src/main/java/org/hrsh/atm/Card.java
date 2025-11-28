package org.hrsh.atm;

import java.time.LocalDateTime;

public class Card {
    private String number;
    private String name;
    private int cvv;
    private String pin;
    private LocalDateTime issuedAt;
    private LocalDateTime expireAt;

    public Card(String number, String name, String pin, LocalDateTime expireAt) {
        this.number = number;
        this.name = name;
        this.pin = pin;
        this.expireAt = expireAt;
        this.issuedAt = LocalDateTime.now();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }
}
