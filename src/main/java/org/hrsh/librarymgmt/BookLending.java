package org.hrsh.librarymgmt;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookLending {
    private final String id;
    private String bookItemBarcode;
    private LocalDateTime borrowedAt;
    private LocalDateTime dueDate;
    private String memberId;
    private LocalDateTime returnedAt;

    public BookLending() {
        this.id = UUID.randomUUID().toString();
    }

    public BookLending(String bookItemBarcode, LocalDateTime borrowedAt, LocalDateTime dueDate, String memberId) {
        this.id = UUID.randomUUID().toString();
        this.bookItemBarcode = bookItemBarcode;
        this.borrowedAt = borrowedAt;
        this.dueDate = dueDate;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public String getBookItemBarcode() {
        return bookItemBarcode;
    }

    public void setBookItemBarcode(String bookItemBarcode) {
        this.bookItemBarcode = bookItemBarcode;
    }

    public LocalDateTime getBorrowedAt() {
        return borrowedAt;
    }

    public void setBorrowedAt(LocalDateTime borrowedAt) {
        this.borrowedAt = borrowedAt;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDateTime getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }
}
