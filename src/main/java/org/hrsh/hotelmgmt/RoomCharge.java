package org.hrsh.hotelmgmt;

import java.util.Date;
import java.util.UUID;

public abstract class RoomCharge {
    private String id;
    private String name;
    private Date issueAt;
    private Invoice invoice;
    private double amount;

    public RoomCharge(String name, double amount) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.amount = amount;
        this.issueAt = new Date();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(Date issueAt) {
        this.issueAt = issueAt;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
