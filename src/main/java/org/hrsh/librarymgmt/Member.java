package org.hrsh.librarymgmt;

import java.time.LocalDateTime;
import java.util.UUID;

public class Member extends User {
    private String id;
    private MemberCard memberCard;
    private int totalBooksCheckedOut;
    private LocalDateTime membershipCreatedAt;

    public Member(String name, String phone, Account account, MemberCard memberCard) {
        super(name, phone, account);
        this.id = UUID.randomUUID().toString();
        this.memberCard = memberCard;
        this.totalBooksCheckedOut = 0;
        this.membershipCreatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MemberCard getMemberCard() {
        return memberCard;
    }

    public void setMemberCard(MemberCard memberCard) {
        this.memberCard = memberCard;
    }

    public int getTotalBooksCheckedOut() {
        return totalBooksCheckedOut;
    }

    public void setTotalBooksCheckedOut(int totalBooksCheckedOut) {
        this.totalBooksCheckedOut = totalBooksCheckedOut;
    }

    public LocalDateTime getMembershipCreatedAt() {
        return membershipCreatedAt;
    }

    public void setMembershipCreatedAt(LocalDateTime membershipCreatedAt) {
        this.membershipCreatedAt = membershipCreatedAt;
    }
}
