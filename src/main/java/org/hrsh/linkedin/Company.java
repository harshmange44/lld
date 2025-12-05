package org.hrsh.linkedin;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Company {
    private String id;
    private String name;
    private String about;
    private CompanyType companyType;
    private List<Member> members;
    private List<Job> jobs;
    private Member createdBy;
    private Date createdAt;
    private Date updatedAt;
    private CompanyStatus companyStatus;
    private Location location;

    public Company(String name, CompanyType companyType, Member createdBy) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.companyType = companyType;
        this.createdBy = createdBy;
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.companyStatus = CompanyStatus.ACTIVE;
        this.members = new CopyOnWriteArrayList<>(); // Thread-safe
        this.jobs = new CopyOnWriteArrayList<>(); // Thread-safe
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setPageType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public List<Member> getMembers() {
        return new ArrayList<>(members); // Return defensive copy
    }

    public void setMembers(List<Member> members) {
        this.members = members != null ? new CopyOnWriteArrayList<>(members) : new CopyOnWriteArrayList<>();
    }

    public List<Job> getJobs() {
        return new ArrayList<>(jobs); // Return defensive copy
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs != null ? new CopyOnWriteArrayList<>(jobs) : new CopyOnWriteArrayList<>();
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

    public CompanyStatus getCompanyStatus() {
        return companyStatus;
    }

    public void setPageStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
