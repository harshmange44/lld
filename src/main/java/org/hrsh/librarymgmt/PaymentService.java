package org.hrsh.librarymgmt;

public class PaymentService {
    public PaymentReceipt process(BillReceipt billReceipt, PaymentMethod paymentMethod) {
        return new PaymentReceipt(paymentMethod, PaymentStatus.SUCCESSFUL, billReceipt.getTotalAmount());
    }
}
