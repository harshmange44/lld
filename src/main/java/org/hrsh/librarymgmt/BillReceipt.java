package org.hrsh.librarymgmt;

import java.util.List;

public class BillReceipt {
    private String id;
    private double totalAmount;
    private List<Invoice> invoiceList;

    public BillReceipt(double totalAmount, List<Invoice> invoiceList) {
        this.totalAmount = totalAmount;
        this.invoiceList = invoiceList;
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
        this.invoiceList = invoiceList;
    }
}
