package org.hrsh.movieticketbookingsystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> userNotifications = new ConcurrentHashMap<>();
    private static final int RESERVATION_WARNING_MINUTES = 5; // Warn 5 minutes before expiry

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

    /**
     * Start background service to check for reservation expiry warnings
     */
    public void startNotificationService(Map<String, Reservation> reservations) {
        Thread notificationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(60000); // Check every minute
                    checkAndSendReservationWarnings(reservations);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        notificationThread.setDaemon(true);
        notificationThread.start();
    }

    private void checkAndSendReservationWarnings(Map<String, Reservation> reservations) {
        LocalDateTime warningTime = LocalDateTime.now().plusMinutes(RESERVATION_WARNING_MINUTES);

        for (Reservation reservation : reservations.values()) {
            LocalDateTime expiryTime = reservation.getReservedAt()
                    .plusMinutes(Reservation.RESERVATION_TIMEOUT_MINUTES);
            
            // Check if within warning window
            if (warningTime.isAfter(expiryTime.minusMinutes(RESERVATION_WARNING_MINUTES)) 
                    && LocalDateTime.now().isBefore(expiryTime)) {
                long minutesLeft = java.time.temporal.ChronoUnit.MINUTES.between(
                        LocalDateTime.now(), expiryTime);
                sendNotification(reservation.getUser(), NotificationType.OTHER,
                        "Reservation expires in " + minutesLeft + " minutes. Please complete booking.");
            }
        }
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

