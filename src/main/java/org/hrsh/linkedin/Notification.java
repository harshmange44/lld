package org.hrsh.linkedin;

import java.util.UUID;

public class Notification {
    private String id;
    private NotificationType notificationType;
    private String desc;
    private boolean isRead;

    public Notification(String id, NotificationType notificationType, String desc, boolean isRead) {
        this.id = id;
        this.notificationType = notificationType;
        this.desc = desc;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
