package org.hrsh.stockbrokeragesystem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();

    public void sendNotification(User user, NotificationType type, String description) {
        if (user == null || type == null || description == null) {
            return;
        }

        Notification notification = new Notification(
                UUID.randomUUID().toString(),
                type,
                description,
                false
        );

        String userId = user.getId();
        userNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(notification);
    }

    public void notifyStockPriceUpdate(Stock stock, double newPrice) {
        // Notify all users tracking this stock (simplified - would need tracking mechanism)
        String message = "Stock " + stock.getName() + " price updated to: $" + newPrice;
        // In real system, would notify only users watching this stock
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

