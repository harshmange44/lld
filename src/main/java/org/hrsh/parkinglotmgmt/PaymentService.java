package org.hrsh.parkinglotmgmt;

public class PaymentService {
    public PaymentReceipt process(BillReceipt billReceipt, PaymentMethod paymentMethod) {
        return new PaymentReceipt(paymentMethod, PaymentStatus.SUCCESSFUL, billReceipt.getTotalAmount());
    }
}
