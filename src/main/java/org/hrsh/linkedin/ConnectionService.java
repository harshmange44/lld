package org.hrsh.linkedin;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionService {
    /**
     * Service for managing connections between members
     */

    public ConnectionInvitation sendConnectionRequest(Member fromMember, Member toMember,
                                                     Map<String, ConnectionInvitation> invitations,
                                                     NotificationService notificationService) {
        if (fromMember == null || toMember == null) {
            throw new IllegalArgumentException("Members cannot be null");
        }

        if (fromMember.getId().equals(toMember.getId())) {
            throw new IllegalStateException("Cannot send connection request to yourself");
        }

        // Check if already connected
        if (areConnected(fromMember, toMember)) {
            throw new IllegalStateException("Members are already connected");
        }

        // Check if invitation already exists
        boolean existingInvitation = invitations.values().stream()
                .anyMatch(inv -> inv.getInvitedBy().getId().equals(fromMember.getId())
                        && inv.getInvitedTo() != null && inv.getInvitedTo().getId().equals(toMember.getId())
                        && inv.getConnectionInvitationStatus() == ConnectionInvitationStatus.PENDING);

        if (existingInvitation) {
            throw new IllegalStateException("Connection request already sent");
        }

        // Create invitation
        ConnectionInvitation invitation = new ConnectionInvitation();
        invitation.setId(UUID.randomUUID().toString());
        invitation.setInvitedBy(fromMember);
        invitation.setInvitedTo(toMember);
        invitation.setConnectionInvitationStatus(ConnectionInvitationStatus.PENDING);

        // Store invitation
        invitations.put(invitation.getId(), invitation);

        // Add to member's invitation list
        if (toMember.getConnectionInvitations() == null) {
            toMember.setConnectionInvitations(new CopyOnWriteArrayList<>());
        }
        toMember.getConnectionInvitations().add(invitation);

        // Send notification
        notificationService.sendNotification(toMember, NotificationType.CONNECTION_REQUEST,
                fromMember.getName() + " wants to connect with you");

        return invitation;
    }

    public synchronized boolean acceptConnectionRequest(ConnectionInvitation invitation,
                                                       NotificationService notificationService) {
        if (invitation == null) {
            return false;
        }

        if (invitation.getConnectionInvitationStatus() != ConnectionInvitationStatus.PENDING) {
            return false;
        }

        Member fromMember = invitation.getInvitedBy();
        Member toMember = invitation.getInvitedTo();

        // Accept invitation
        invitation.acceptInvitation();

        // Add to each other's connections
        if (fromMember.getMemberConnectionsList() == null) {
            fromMember.setMemberConnectionsList(new CopyOnWriteArrayList<>());
        }
        if (toMember.getMemberConnectionsList() == null) {
            toMember.setMemberConnectionsList(new CopyOnWriteArrayList<>());
        }

        fromMember.getMemberConnectionsList().add(toMember);
        toMember.getMemberConnectionsList().add(fromMember);

        // Send notifications
        notificationService.sendNotification(fromMember, NotificationType.CONNECTION_ACCEPTED,
                toMember.getName() + " accepted your connection request");

        return true;
    }

    public boolean areConnected(Member member1, Member member2) {
        if (member1 == null || member2 == null) {
            return false;
        }

        List<Member> connections1 = member1.getMemberConnectionsList();
        if (connections1 == null || connections1.isEmpty()) {
            return false;
        }

        return connections1.stream()
                .anyMatch(m -> m.getId().equals(member2.getId()));
    }
}

