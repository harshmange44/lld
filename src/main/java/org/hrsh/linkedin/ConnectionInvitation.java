package org.hrsh.linkedin;

public class ConnectionInvitation {
    private String id;
    private Member invitedBy;
    private Member invitedTo; // Added for tracking recipient
    private ConnectionInvitationStatus connectionInvitationStatus;

    public ConnectionInvitation() {
        this.connectionInvitationStatus = ConnectionInvitationStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void acceptInvitation() {
        this.connectionInvitationStatus = ConnectionInvitationStatus.ACCEPTED;
    }

    public void rejectInvitation() {
        this.connectionInvitationStatus = ConnectionInvitationStatus.REJECTED;
    }

    public void withdrawInvitation() {
        this.connectionInvitationStatus = ConnectionInvitationStatus.WITHDRAW;
    }

    public Member getInvitedBy() {
        return invitedBy;
    }

    public void setInvitedBy(Member invitedBy) {
        this.invitedBy = invitedBy;
    }

    public Member getInvitedTo() {
        return invitedTo;
    }

    public void setInvitedTo(Member invitedTo) {
        this.invitedTo = invitedTo;
    }

    public ConnectionInvitationStatus getConnectionInvitationStatus() {
        return connectionInvitationStatus;
    }

    public void setConnectionInvitationStatus(ConnectionInvitationStatus connectionInvitationStatus) {
        this.connectionInvitationStatus = connectionInvitationStatus;
    }
}
