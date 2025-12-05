package org.hrsh.linkedin;

import java.time.LocalDateTime;

public class Comment extends Content {
    private LocalDateTime createdAt; // Added for tracking

    public Comment() {
        super();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
