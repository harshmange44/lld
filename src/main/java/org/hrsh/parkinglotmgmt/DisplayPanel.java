package org.hrsh.parkinglotmgmt;

public class DisplayPanel {
    private String id;
    private String displayMessage;

    public DisplayPanel(String id, String displayMessage) {
        this.id = id;
        this.displayMessage = displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }
}
