package org.hrsh.atm;

import java.time.LocalDateTime;

public class Card {
    private String number;
    private String name;
    private int cvv;
    private String pin;
    private LocalDateTime issuedAt;
    private LocalDateTime expireAt;

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getCvv() {
        return cvv;
    }

    public String getPin() {
        return pin;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }
}
