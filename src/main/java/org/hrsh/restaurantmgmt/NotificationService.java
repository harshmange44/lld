package org.hrsh.restaurantmgmt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();

    public void sendNotification(String customerName, NotificationType type, String description) {
        if (customerName == null || type == null || description == null) {
            return;
        }

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                type,
                description,
                false
        );

        userNotifications.computeIfAbsent(customerName, k -> new ArrayList<>()).add(notification);
    }

    public List<Notification> getNotifications(String customerName) {
        return userNotifications.getOrDefault(customerName, Collections.emptyList());
    }

    public void markAsRead(String customerName, String notificationId) {
        List<Notification> notifications = userNotifications.get(customerName);
        if (notifications != null) {
            notifications.stream()
                    .filter(n -> n.getNotificationId().equals(notificationId))
                    .forEach(n -> n.setRead(true));
        }
    }
}

