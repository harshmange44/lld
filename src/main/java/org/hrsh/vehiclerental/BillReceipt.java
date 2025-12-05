package org.hrsh.vehiclerental;

import java.util.List;
import java.util.UUID;

public class BillReceipt {
    private String id;
    private double totalAmount;
    private List<Invoice> invoiceList;

    public BillReceipt(double totalAmount, List<Invoice> invoiceList) {
        this.id = UUID.randomUUID().toString();
        this.totalAmount = totalAmount;
        this.invoiceList = invoiceList != null ? invoiceList : List.of();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList != null ? invoiceList : List.of();
    }
}
