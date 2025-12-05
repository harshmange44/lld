package org.hrsh.linkedin;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class PostService {
    /**
     * Service for managing posts, likes, comments, and feed generation
     */

    public Post createPost(Member member, String text, Media media, Company company) {
        if (member == null || text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Member and text are required");
        }

        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setText(text);
        post.setMedia(media);
        post.setCreatedBy(member);
        post.setLinkedCompany(company);
        post.setCreatedAt(LocalDateTime.now());
        post.setNoOfLikes(BigInteger.ZERO);
        post.setComments(new CopyOnWriteArrayList<>());

        return post;
    }

    public List<Post> generateFeed(Member member, Map<String, Post> posts) {
        if (member == null || posts == null || posts.isEmpty()) {
            return Collections.emptyList();
        }

        List<Member> connections = member.getMemberConnectionsList();
        if (connections == null || connections.isEmpty()) {
            return Collections.emptyList();
        }

        Set<String> connectionIds = connections.stream()
                .map(Member::getId)
                .collect(Collectors.toSet());

        // Get posts from connections, sorted by creation time (newest first)
        return posts.values().stream()
                .filter(post -> connectionIds.contains(post.getCreatedBy().getId()))
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .limit(50) // Limit feed size
                .collect(Collectors.toList());
    }

    public synchronized void likePost(Post post, Member member) {
        if (post == null || member == null) {
            return;
        }

        // Initialize likes if null
        if (post.getNoOfLikes() == null) {
            post.setNoOfLikes(BigInteger.ZERO);
        }

        // Increment likes
        post.setNoOfLikes(post.getNoOfLikes().add(BigInteger.ONE));

        // Track who liked (optional - for notification)
        if (post.getLikedBy() == null) {
            post.setLikedBy(new CopyOnWriteArrayList<>());
        }
        post.getLikedBy().add(member);
    }

    public synchronized Comment addComment(Post post, Member member, String commentText) {
        if (post == null || member == null || commentText == null || commentText.isEmpty()) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setText(commentText);
        comment.setCreatedBy(member);
        comment.setParentContentId(post);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setNoOfLikes(BigInteger.ZERO);

        if (post.getComments() == null) {
            post.setComments(new CopyOnWriteArrayList<>());
        }
        post.getComments().add(comment);

        return comment;
    }
}

