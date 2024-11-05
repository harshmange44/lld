package org.hrsh.librarymgmt;

import java.util.List;

public class LibraryMgmtSystem {
    /**
     * 1. Library contains multiple books
     * 2. Each Book can have multiple copies
     * 3. Each Book Copy can be borrowed by members
     * 4. Librarian can issue & return book copies
     * 5. Some Librarians can add, modify books & book copies as well sign up members
     * 6. Book Copies should be placed at specific Rack, hence easy to monitor, manage & identify
     * 7. Search books by Title, Author, Category, Publication Date
     * 8. Notify when book copies become available, return due date is near or already passed
     * 9. Member can check out one or more book copies of diff Books
     * 10. Limit on how many books a member can issue, how many days a member can keep a book
     * 11. Fine on book copy returned after a due date
     * 12. Each Book Copy and Member Card will have unique barcode
    */

    private List<Library> libraries;
}
