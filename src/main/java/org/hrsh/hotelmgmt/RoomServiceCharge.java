package org.hrsh.hotelmgmt;

import java.util.Date;

public class RoomServiceCharge extends RoomCharge {
    private boolean isChargeable;
    private Date requestDateTime;

    public RoomServiceCharge(String name, double amount, boolean isChargeable, Date requestDateTime) {
        super(name, amount);
        this.isChargeable = isChargeable;
        this.requestDateTime = requestDateTime;
    }

    public boolean isChargeable() {
        return isChargeable;
    }

    public void setChargeable(boolean chargeable) {
        isChargeable = chargeable;
    }

    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }
}
