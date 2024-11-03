package org.hrsh.linkedin;

import java.util.Date;
import java.util.List;

public class Job {
    private String id;
    private String title;
    private String desc;
    private List<Skill> requiredSkills;
    private int noOfApplications;
    private int noOfVacancies;
    private JobStatus jobStatus;
    private Member createdBy;
    private Member hiringManagerContact;
    private Date createdAt;
    private Date updatedAt;
}
