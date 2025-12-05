package org.hrsh.vehiclerental;

import java.time.LocalDateTime;

public class DrivingLicense {
    private String number;
    private LocalDateTime issuedAt;
    private LocalDateTime expireDate;

    public DrivingLicense(String number, LocalDateTime issuedAt, LocalDateTime expireDate) {
        this.number = number;
        this.issuedAt = issuedAt;
        this.expireDate = expireDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }

    public boolean isValid() {
        return expireDate != null && expireDate.isAfter(LocalDateTime.now());
    }
}
