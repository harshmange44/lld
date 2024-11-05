package org.hrsh.librarymgmt;

import java.time.LocalDateTime;

public class Member extends User {
    private MemberCard memberCard;
    private int totalBooksCheckedOut;
    private LocalDateTime membershipCreatedAt;
    private final PaymentService paymentService;

    public Member(MemberCard memberCard) {
        this.memberCard = memberCard;
        this.totalBooksCheckedOut = 0;
        this.membershipCreatedAt = LocalDateTime.now();
        this.paymentService = new PaymentService();
    }

    public BookLending checkoutBook(BookItem bookItem) {
        BookLending bookLending = new BookLending();
        return bookLending.lendBook(bookItem.getBarcode(), getMemberCard().getBarcode());
    }

    public boolean returnBook(BookItem bookItem) {
        return true;
    }

    public boolean renewBook(BookItem bookItem) {
        return true;
    }

    public Fine checkForFine(BookItem bookItem) {
        return new Fine();
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
