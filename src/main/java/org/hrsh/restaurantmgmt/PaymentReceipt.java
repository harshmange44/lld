package org.hrsh.restaurantmgmt;

import java.util.UUID;

public class PaymentReceipt {
    private String transactionId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private double amountPayed;

    public PaymentReceipt(PaymentMethod paymentMethod, PaymentStatus paymentStatus, double amountPayed) {
        this.transactionId = UUID.randomUUID().toString();
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amountPayed = amountPayed;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getAmountPayed() {
        return amountPayed;
    }

    public void setAmountPayed(double amountPayed) {
        this.amountPayed = amountPayed;
    }
}
