package org.hrsh.vehiclerental;

import java.util.*;
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

