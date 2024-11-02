package org.hrsh.facebook;

import java.util.Date;

public class Message {
    private String id;
    private Member messageBy;
    private Member messageTo;
    private MessageBody messageBody;
    private MessageStatus messageStatus;
    private Date createdAt;
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public Member getMessageBy() {
        return messageBy;
    }

    public void setMessageBy(Member messageBy) {
        this.messageBy = messageBy;
    }

    public Member getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(Member messageTo) {
        this.messageTo = messageTo;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
