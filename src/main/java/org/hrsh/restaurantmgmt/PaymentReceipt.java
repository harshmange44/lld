package org.hrsh.restaurantmgmt;

public class PaymentReceipt {
    private String transactionId;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    private double amountPayed;

    public PaymentReceipt(PaymentMethod paymentMethod, PaymentStatus paymentStatus, double amountPayed) {
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.amountPayed = amountPayed;
    }
}
