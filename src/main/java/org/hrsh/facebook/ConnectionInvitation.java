package org.hrsh.facebook;

public class ConnectionInvitation {
    private String id;
    private Member invitedBy;
    private ConnectionInvitationStatus connectionInvitationStatus;

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

    public ConnectionInvitationStatus getConnectionInvitationStatus() {
        return connectionInvitationStatus;
    }

    public void setConnectionInvitationStatus(ConnectionInvitationStatus connectionInvitationStatus) {
        this.connectionInvitationStatus = connectionInvitationStatus;
    }
}
