package org.hrsh.linkedin;

import java.util.Date;
import java.util.List;

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

    public String getId() {
        return id;
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

    public CompanyType getPageType() {
        return companyType;
    }

    public void setPageType(CompanyType companyType) {
        this.companyType = companyType;
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

    public CompanyStatus getPageStatus() {
        return companyStatus;
    }

    public void setPageStatus(CompanyStatus companyStatus) {
        this.companyStatus = companyStatus;
    }
}
