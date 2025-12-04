package org.hrsh.hotelmgmt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();

    public void sendNotification(Guest guest, NotificationType type, String description) {
        if (guest == null || type == null || description == null) {
            return;
        }

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                type,
                description,
                false
        );

        String userId = guest.getId();
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public List<Notification> getNotifications(String userId) {
        return userNotifications.getOrDefault(userId, Collections.emptyList());
    }

    public void markAsRead(String userId, String notificationId) {
        List<Notification> notifications = userNotifications.get(userId);
        if (notifications != null) {
            notifications.stream()
                    .filter(n -> n.getNotificationId().equals(notificationId))
                    .forEach(n -> n.setRead(true));
        }
    }
}

