package org.hrsh.librarymgmt;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationService {
    private final Map<String, List<Notification>> memberNotifications = new ConcurrentHashMap<>();
    private static final int DAYS_BEFORE_DUE_REMINDER = 2; // Remind 2 days before due date

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

    /**
     * Start background service to check for due date reminders
     */
    public void startNotificationService(Map<String, BookLending> activeLendings, Map<String, Member> members) {
        Thread notificationThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(86400000); // Check once per day
                    checkAndSendDueDateReminders(activeLendings, members);
                    checkAndSendOverdueNotifications(activeLendings, members);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        notificationThread.setDaemon(true);
        notificationThread.start();
    }

    private void checkAndSendDueDateReminders(Map<String, BookLending> activeLendings, Map<String, Member> members) {
        LocalDateTime reminderDate = LocalDateTime.now().plusDays(DAYS_BEFORE_DUE_REMINDER);

        for (BookLending lending : activeLendings.values()) {
            if (lending.getDueDate().toLocalDate().equals(reminderDate.toLocalDate())) {
                Member member = members.get(lending.getMemberId());
                if (member != null) {
                    sendNotification(member, NotificationType.DATE_DUE,
                            "Reminder: Book due in " + DAYS_BEFORE_DUE_REMINDER + " days. Due date: " 
                            + lending.getDueDate().toLocalDate());
                }
            }
        }
    }

    private void checkAndSendOverdueNotifications(Map<String, BookLending> activeLendings, Map<String, Member> members) {
        LocalDateTime now = LocalDateTime.now();

        for (BookLending lending : activeLendings.values()) {
            if (now.isAfter(lending.getDueDate())) {
                Member member = members.get(lending.getMemberId());
                if (member != null) {
                    long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(lending.getDueDate(), now);
                    sendNotification(member, NotificationType.DATE_DUE,
                            "Overdue: Book is " + daysOverdue + " days overdue. Please return immediately.");
                }
            }
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

