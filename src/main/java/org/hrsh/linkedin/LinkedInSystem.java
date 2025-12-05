package org.hrsh.linkedin;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class LinkedInSystem {
    /**
     * Main orchestrator for LinkedIn System
     * Handles: Member Management, Connections, Posts, Messages, Search
     */

    private final Map<String, Member> members;
    private final Map<String, Company> companies;
    private final Map<String, Group> groups;
    private final Map<String, Post> posts; // postId -> Post
    private final Map<String, Message> messages; // messageId -> Message
    private final Map<String, ConnectionInvitation> invitations; // invitationId -> ConnectionInvitation

    private final ConnectionService connectionService;
    private final PostService postService;
    private final MessageService messageService;
    private final NotificationService notificationService;
    private final SearchIndex searchIndex;

    public LinkedInSystem() {
        this.members = new ConcurrentHashMap<>();
        this.companies = new ConcurrentHashMap<>();
        this.groups = new ConcurrentHashMap<>();
        this.posts = new ConcurrentHashMap<>();
        this.messages = new ConcurrentHashMap<>();
        this.invitations = new ConcurrentHashMap<>();

        this.searchIndex = new SearchIndex();
        this.connectionService = new ConnectionService();
        this.postService = new PostService();
        this.messageService = new MessageService();
        this.notificationService = new NotificationService();
    }

    // Member Management
    public void registerMember(Member member) {
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.put(member.getId(), member);
        // Index member for search
        searchIndex.addMemberName(member);
    }

    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    // Connection Management
    public ConnectionInvitation sendConnectionRequest(String fromMemberId, String toMemberId) {
        if (fromMemberId == null || toMemberId == null) {
            throw new IllegalArgumentException("Member IDs cannot be null");
        }

        Member fromMember = members.get(fromMemberId);
        Member toMember = members.get(toMemberId);

        if (fromMember == null || toMember == null) {
            throw new IllegalArgumentException("Member not found");
        }

        return connectionService.sendConnectionRequest(fromMember, toMember, invitations, notificationService);
    }

    public synchronized boolean acceptConnectionRequest(String invitationId, String memberId) {
        if (invitationId == null || memberId == null) {
            throw new IllegalArgumentException("Invitation ID and Member ID cannot be null");
        }

        ConnectionInvitation invitation = invitations.get(invitationId);
        if (invitation == null) {
            return false;
        }

        // Verify the invitation is for this member
        if (!invitation.getInvitedTo().getId().equals(memberId)) {
            throw new IllegalStateException("This invitation does not belong to the member");
        }

        return connectionService.acceptConnectionRequest(invitation, notificationService);
    }

    public boolean rejectConnectionRequest(String invitationId, String memberId) {
        if (invitationId == null || memberId == null) {
            throw new IllegalArgumentException("Invitation ID and Member ID cannot be null");
        }

        ConnectionInvitation invitation = invitations.get(invitationId);
        if (invitation == null) {
            return false;
        }

        // Verify the invitation is for this member
        if (invitation.getInvitedTo() == null || !invitation.getInvitedTo().getId().equals(memberId)) {
            throw new IllegalStateException("This invitation does not belong to the member");
        }

        invitation.rejectInvitation();
        return true;
    }

    public List<Member> getConnections(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            return Collections.emptyList();
        }
        return member.getMemberConnectionsList() != null 
                ? new ArrayList<>(member.getMemberConnectionsList()) 
                : Collections.emptyList();
    }

    // Post Management
    public synchronized Post createPost(String memberId, String text, Media media, String companyId) {
        if (memberId == null || text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Member ID and text are required");
        }

        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }

        Post post = postService.createPost(member, text, media, companyId != null ? companies.get(companyId) : null);
        posts.put(post.getId(), post);

        // Index post for search
        searchIndex.addPostWords(post);

        // Send notification to connections
        notificationService.notifyConnectionsAboutPost(member, post, getConnections(memberId));

        return post;
    }

    public List<Post> getFeed(String memberId) {
        Member member = members.get(memberId);
        if (member == null) {
            return Collections.emptyList();
        }

        return postService.generateFeed(member, posts);
    }

    public synchronized boolean likePost(String memberId, String postId) {
        if (memberId == null || postId == null) {
            throw new IllegalArgumentException("Member ID and Post ID cannot be null");
        }

        Post post = posts.get(postId);
        if (post == null) {
            return false;
        }

        postService.likePost(post, members.get(memberId));
        return true;
    }

    public synchronized Comment addComment(String memberId, String postId, String commentText) {
        if (memberId == null || postId == null || commentText == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Post post = posts.get(postId);
        Member member = members.get(memberId);

        if (post == null || member == null) {
            throw new IllegalArgumentException("Post or Member not found");
        }

        return postService.addComment(post, member, commentText);
    }

    // Message Management
    public synchronized Message sendMessage(String fromMemberId, String toMemberId, String messageText) {
        if (fromMemberId == null || toMemberId == null || messageText == null) {
            throw new IllegalArgumentException("All parameters are required");
        }

        Member fromMember = members.get(fromMemberId);
        Member toMember = members.get(toMemberId);

        if (fromMember == null || toMember == null) {
            throw new IllegalArgumentException("Member not found");
        }

        // Check if members are connected
        if (!connectionService.areConnected(fromMember, toMember)) {
            throw new IllegalStateException("Members must be connected to send messages");
        }

        Message message = messageService.sendMessage(fromMember, toMember, messageText);
        messages.put(message.getId(), message);

        // Send notification
        notificationService.sendNotification(toMember, NotificationType.MESSAGE_RECEIVED,
                "New message from " + fromMember.getName());

        return message;
    }

    public List<Message> getConversation(String memberId1, String memberId2) {
        return messageService.getConversation(memberId1, memberId2, messages);
    }

    // Search
    public List<Member> searchMembers(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        return searchIndex.searchMembers(query);
    }

    public List<Post> searchPosts(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        return searchIndex.searchPosts(query);
    }

    public List<Company> searchCompanies(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }
        return searchIndex.searchPages(query);
    }

    // Company Management
    public void addCompany(Company company) {
        if (company == null || company.getId() == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }
        companies.put(company.getId(), company);
        searchIndex.addCompanyName(company);
    }

    // Group Management
    public void createGroup(Group group, String creatorId) {
        if (group == null || creatorId == null) {
            throw new IllegalArgumentException("Group and creator ID cannot be null");
        }

        Member creator = members.get(creatorId);
        if (creator == null) {
            throw new IllegalArgumentException("Creator not found");
        }

        group.setCreatedBy(creator);
        groups.put(group.getId(), group);
        searchIndex.addGroupName(group);
    }

    public boolean joinGroup(String groupId, String memberId) {
        Group group = groups.get(groupId);
        Member member = members.get(memberId);

        if (group == null || member == null) {
            return false;
        }

        synchronized (group) {
            if (group.getMembers() == null) {
                group.setMembers(new CopyOnWriteArrayList<>());
            }
            if (!group.getMembers().contains(member)) {
                group.addMember(member);
                return true;
            }
        }
        return false;
    }

    // Getters
    public Post getPost(String postId) {
        return posts.get(postId);
    }

    public List<ConnectionInvitation> getPendingInvitations(String memberId) {
        return invitations.values().stream()
                .filter(inv -> inv.getInvitedTo() != null && inv.getInvitedTo().getId().equals(memberId))
                .filter(inv -> inv.getConnectionInvitationStatus() == ConnectionInvitationStatus.PENDING)
                .collect(Collectors.toList());
    }
}

