package org.hrsh.parkinglotmgmt;

public class CustomerPortal extends DisplayPanel {
    private final BillingService billingService;
    private final PaymentService paymentService;

    public CustomerPortal(String id, String displayMessage) {
        super(id, displayMessage);
        this.billingService = new BillingService();
        this.paymentService = new PaymentService();
    }

    public BillReceipt processBilling(Spot spot, ParkingTicket parkingTicket, double additionalCharge) {
        return billingService.generateBill(spot, parkingTicket, additionalCharge);
    }

    public PaymentReceipt processPayment(BillReceipt billReceipt, PaymentMethod paymentMethod) {
        return paymentService.process(billReceipt, paymentMethod);
    }
}
