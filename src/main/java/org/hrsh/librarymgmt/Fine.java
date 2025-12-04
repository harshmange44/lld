package org.hrsh.librarymgmt;

import java.util.UUID;

public class Fine {
    private String id;
    private String bookItemBarcode;
    private String memberId;
    private double amount;
    private boolean isPaid;

    public Fine(String bookItemBarcode, String memberId, double amount) {
        this.id = UUID.randomUUID().toString();
        this.bookItemBarcode = bookItemBarcode;
        this.memberId = memberId;
        this.amount = amount;
        this.isPaid = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookItemBarcode() {
        return bookItemBarcode;
    }

    public void setBookItemBarcode(String bookItemBarcode) {
        this.bookItemBarcode = bookItemBarcode;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
