package org.hrsh.movieticketbookingsystem;

import java.util.UUID;

public class Notification {
    private String notificationId;
    private NotificationType notificationType;
    private String desc;
    private boolean isRead;

    public Notification(String notificationId, NotificationType notificationType, String desc, boolean isRead) {
        this.notificationId = notificationId;
        this.notificationType = notificationType;
        this.desc = desc;
        this.isRead = isRead;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}

