package org.hrsh.linkedin;

import java.util.*;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> memberNotifications = new ConcurrentHashMap<>();

    public void sendNotification(Member member, NotificationType type, String description) {
        if (member == null || type == null || description == null) {
            return;
        }

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                type,
                description,
                false
        );

        String memberId = member.getId();
        memberNotifications.computeIfAbsent(memberId, k -> new ArrayList<>()).add(notification);
    }

    public void notifyConnectionsAboutPost(Member author, Post post, List<Member> connections) {
        if (author == null || post == null || connections == null) {
            return;
        }

        for (Member connection : connections) {
            sendNotification(connection, NotificationType.POST_CREATED,
                    author.getName() + " created a new post");
        }
    }

    public List<Notification> getNotifications(String memberId) {
        return memberNotifications.getOrDefault(memberId, Collections.emptyList());
    }

    public void markAsRead(String memberId, String notificationId) {
        List<Notification> notifications = memberNotifications.get(memberId);
        if (notifications != null) {
            notifications.stream()
                    .filter(n -> n.getNotificationId().equals(notificationId))
                    .forEach(n -> n.setRead(true));
        }
    }
}

