package org.hrsh.linkedin;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public Member(String name, String phone, Account account) {
        super(name, phone, account);
        this.workList = new CopyOnWriteArrayList<>();
        this.educationList = new CopyOnWriteArrayList<>();
        this.skills = new CopyOnWriteArrayList<>();
        this.accomplishments = new CopyOnWriteArrayList<>();
        this.memberFollowsList = new CopyOnWriteArrayList<>();
        this.memberConnectionsList = new CopyOnWriteArrayList<>();
        this.recommendations = new CopyOnWriteArrayList<>();
        this.companiesFollow = new CopyOnWriteArrayList<>();
        this.groups = new CopyOnWriteArrayList<>();
        this.connectionInvitations = new CopyOnWriteArrayList<>();
    }

    public List<Work> getWorkList() {
        return new ArrayList<>(workList); // Return defensive copy
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList != null ? new CopyOnWriteArrayList<>(workList) : new CopyOnWriteArrayList<>();
    }

    public List<Education> getEducationList() {
        return new ArrayList<>(educationList); // Return defensive copy
    }

    public void setEducationList(List<Education> educationList) {
        this.educationList = educationList != null ? new CopyOnWriteArrayList<>(educationList) : new CopyOnWriteArrayList<>();
    }

    public List<Skill> getSkills() {
        return new ArrayList<>(skills); // Return defensive copy
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills != null ? new CopyOnWriteArrayList<>(skills) : new CopyOnWriteArrayList<>();
    }

    public List<Accomplishment> getAccomplishments() {
        return new ArrayList<>(accomplishments); // Return defensive copy
    }

    public void setAccomplishments(List<Accomplishment> accomplishments) {
        this.accomplishments = accomplishments != null ? new CopyOnWriteArrayList<>(accomplishments) : new CopyOnWriteArrayList<>();
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<Member> getMemberFollowsList() {
        return new ArrayList<>(memberFollowsList); // Return defensive copy
    }

    public void setMemberFollowsList(List<Member> memberFollowsList) {
        this.memberFollowsList = memberFollowsList != null ? new CopyOnWriteArrayList<>(memberFollowsList) : new CopyOnWriteArrayList<>();
    }

    public List<Member> getMemberConnectionsList() {
        return new ArrayList<>(memberConnectionsList); // Return defensive copy
    }

    public void setMemberConnectionsList(List<Member> memberConnectionsList) {
        this.memberConnectionsList = memberConnectionsList != null ? new CopyOnWriteArrayList<>(memberConnectionsList) : new CopyOnWriteArrayList<>();
    }

    public List<Recommendation> getRecommendations() {
        return new ArrayList<>(recommendations); // Return defensive copy
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations != null ? new CopyOnWriteArrayList<>(recommendations) : new CopyOnWriteArrayList<>();
    }

    public List<Company> getCompaniesFollow() {
        return new ArrayList<>(companiesFollow); // Return defensive copy
    }

    public void setCompaniesFollow(List<Company> companiesFollow) {
        this.companiesFollow = companiesFollow != null ? new CopyOnWriteArrayList<>(companiesFollow) : new CopyOnWriteArrayList<>();
    }

    public List<Group> getGroups() {
        return new ArrayList<>(groups); // Return defensive copy
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups != null ? new CopyOnWriteArrayList<>(groups) : new CopyOnWriteArrayList<>();
    }

    public List<ConnectionInvitation> getConnectionInvitations() {
        return new ArrayList<>(connectionInvitations); // Return defensive copy
    }

    public void setConnectionInvitations(List<ConnectionInvitation> connectionInvitations) {
        this.connectionInvitations = connectionInvitations != null ? new CopyOnWriteArrayList<>(connectionInvitations) : new CopyOnWriteArrayList<>();
    }
}
