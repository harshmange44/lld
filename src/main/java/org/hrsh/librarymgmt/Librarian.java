package org.hrsh.librarymgmt;

import java.time.LocalDateTime;

public class Librarian extends User {
    private LibraryCard libraryCard;
    private LocalDateTime createdAt;

    public BookItem addBookItem(BookItem bookItem) {
        return new BookItem();
    }

    public BookItem updateBookItem(BookItem bookItem) {
        return new BookItem();
    }

    public boolean blockMember(Member member) {
        return true;
    }

    public boolean unblockMember(Member member) {
        return true;
    }

    public boolean cancelMembership(Member member) {
        return true;
    }
}
