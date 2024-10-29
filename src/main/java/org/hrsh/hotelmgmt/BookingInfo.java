package org.hrsh.hotelmgmt;

public class BookingInfo {
    private String bookingId;
    private String transactionId;
    private Invoice invoice;
    private PaymentStatus paymentStatus;

    public BookingInfo(String bookingId, String transactionId, Invoice invoice, PaymentStatus paymentStatus) {
        this.bookingId = bookingId;
        this.transactionId = transactionId;
        this.invoice = invoice;
        this.paymentStatus = paymentStatus;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
