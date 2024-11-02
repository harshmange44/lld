package org.hrsh.facebook;

import java.util.List;

public class Member extends User {
    private Work work;
    private Education education;
    private List<Member> memberFollowsList;
    private List<Member> memberConnectionsList;
    private List<Page> pages;
    private List<Group> groups;
    private List<ConnectionInvitation> connectionInvitations;
    private List<org.hrsh.facebook.List> privacyList;
    private SearchIndex searchIndex;

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

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
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

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
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

    public List<org.hrsh.facebook.List> getPrivacyList() {
        return privacyList;
    }

    public void setPrivacyList(List<org.hrsh.facebook.List> privacyList) {
        this.privacyList = privacyList;
    }
}
