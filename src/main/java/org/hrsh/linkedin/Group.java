package org.hrsh.linkedin;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Group {
    private String id;
    private String name;
    private String desc;
    private int totalMembers;
    private List<Member> members;
    private Member createdBy;
    private Date createdAt;
    private Date updatedAt;

    public Group(String name, String desc) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.desc = desc;
        this.totalMembers = 0;
        this.members = new CopyOnWriteArrayList<>(); // Thread-safe
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public synchronized void addMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (this.members == null) {
            this.members = new CopyOnWriteArrayList<>();
        }
        if (!this.members.contains(member)) {
            this.members.add(member);
            this.totalMembers++;
            this.updatedAt = new Date();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members); // Return defensive copy
    }

    public void setMembers(List<Member> members) {
        this.members = members != null ? new CopyOnWriteArrayList<>(members) : new CopyOnWriteArrayList<>();
        this.totalMembers = this.members.size();
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Member createdBy) {
        this.createdBy = createdBy;
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
