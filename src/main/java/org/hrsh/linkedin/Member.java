package org.hrsh.linkedin;

import java.util.List;

public class Member extends User {
    private List<Work> workList;
    private List<Education> educationList;
    private List<Skill> skills;
    private List<Accomplishment> accomplishments;
    private Stats stats;
    private List<Member> memberFollowsList;
    private List<Member> memberConnectionsList;
    private List<Recommendation> recommendations;
    private List<Company> companiesFollow;
    private List<Group> groups;
    private List<ConnectionInvitation> connectionInvitations;
    private final SearchIndex searchIndex;

    public Member() {
        this.searchIndex = new SearchIndex();
    }

    public void sendMessage(Message message) {
        message.setMessageBy(this);
        message.setMessageStatus(MessageStatus.SENDING);
    }

    public void createPost(Post post) {
        post.setCreatedBy(this);
        searchIndex.addPostWords(post);
    }

    public void sendConnectionInvitation(ConnectionInvitation connectionInvitation) {
        connectionInvitation.setInvitedBy(this);
    }

    public List<Member> searchMembers(String name) {
        return searchIndex.searchMembers(name);
    }

    public List<Member> getMemberFollowsList() {
        return memberFollowsList;
    }

    public void setMemberFollowsList(List<Member> memberFollowsList) {
        this.memberFollowsList = memberFollowsList;
    }

    public List<Member> getMemberConnectionsList() {
        return memberConnectionsList;
    }

    public void setMemberConnectionsList(List<Member> memberConnectionsList) {
        this.memberConnectionsList = memberConnectionsList;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<ConnectionInvitation> getConnectionInvitations() {
        return connectionInvitations;
    }

    public void setConnectionInvitations(List<ConnectionInvitation> connectionInvitations) {
        this.connectionInvitations = connectionInvitations;
    }
}
