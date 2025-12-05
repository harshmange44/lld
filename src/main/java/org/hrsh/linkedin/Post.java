package org.hrsh.linkedin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Post extends Content {
    private Company linkedCompany;
    private LocalDateTime createdAt; // Added
    private List<Comment> comments; // Added for comments
    private List<Member> likedBy; // Added for tracking likes

    public Post() {
        super();
        this.comments = new CopyOnWriteArrayList<>();
        this.likedBy = new CopyOnWriteArrayList<>();
    }

    public Company getLinkedCompany() {
        return linkedCompany;
    }

    public void setLinkedCompany(Company linkedCompany) {
        this.linkedCompany = linkedCompany;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return new ArrayList<>(comments); // Return defensive copy
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments != null ? new CopyOnWriteArrayList<>(comments) : new CopyOnWriteArrayList<>();
    }

    public List<Member> getLikedBy() {
        return new ArrayList<>(likedBy); // Return defensive copy
    }

    public void setLikedBy(List<Member> likedBy) {
        this.likedBy = likedBy != null ? new CopyOnWriteArrayList<>(likedBy) : new CopyOnWriteArrayList<>();
    }
}
