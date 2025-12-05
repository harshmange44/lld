package org.hrsh.linkedin;

public class MessageBody {
    private String text;

    public MessageBody(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
