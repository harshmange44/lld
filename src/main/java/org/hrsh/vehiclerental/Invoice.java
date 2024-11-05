package org.hrsh.vehiclerental;

public class Invoice {
    private double baseAmount;
    private double taxAmount;
    private double discount;
    private PaymentMethod paymentMethod;

    public Invoice(double baseAmount, double taxAmount, double discount, PaymentMethod paymentMethod) {
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
    }

    public double getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(double baseAmount) {
        this.baseAmount = baseAmount;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
