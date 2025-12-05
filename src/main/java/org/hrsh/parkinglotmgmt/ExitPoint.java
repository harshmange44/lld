package org.hrsh.parkinglotmgmt;

import java.util.UUID;

public class ExitPoint {
    private String id;
    private CustomerPortal customerPortal;

    public ExitPoint(CustomerPortal customerPortal) {
        this.id = UUID.randomUUID().toString();
        this.customerPortal = customerPortal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CustomerPortal getCustomerPortal() {
        return customerPortal;
    }

    public void setCustomerPortal(CustomerPortal customerPortal) {
        this.customerPortal = customerPortal;
    }

    public boolean isPaymentDone(ParkingTicket parkingTicket) {
        // This would check payment status from the ticket
        // For now, return true if customer portal exists
        return customerPortal != null;
    }
}
