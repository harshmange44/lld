package org.hrsh.librarymgmt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

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

    private final Map<String, Library> libraries;
    private final Map<String, BookLending> activeLendings; // bookItemBarcode -> BookLending
    private final Map<String, Member> members;
    private final BookLendingService lendingService;
    private final NotificationService notificationService;

    public LibraryMgmtSystem() {
        this.libraries = new ConcurrentHashMap<>();
        this.activeLendings = new ConcurrentHashMap<>();
        this.members = new ConcurrentHashMap<>();
        this.lendingService = new BookLendingService();
        this.notificationService = new NotificationService();
        
        // Start notification service for due date reminders
        notificationService.startNotificationService(activeLendings, members);
    }

    // Library Management
    public void addLibrary(Library library) {
        if (library == null) {
            throw new IllegalArgumentException("Library cannot be null");
        }
        libraries.put(library.getId(), library);
    }

    public Library getLibrary(String libraryId) {
        return libraries.get(libraryId);
    }

    // Member Management
    public void registerMember(Member member) {
        if (member == null || member.getId() == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.put(member.getId(), member);
    }

    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    // Book Search
    public List<BookItem> searchBooks(String libraryId, String title, String author, Category category) {
        Library library = libraries.get(libraryId);
        if (library == null) {
            throw new IllegalArgumentException("Library not found");
        }

        return library.getBookItems().stream()
                .filter(bookItem -> title == null || bookItem.getTitle().contains(title))
                .filter(bookItem -> author == null || bookItem.getAuthor().getName().contains(author))
                .filter(bookItem -> category == null || bookItem.getCategory() == category)
                .filter(bookItem -> bookItem.getBookItemStatus() == BookItemStatus.AVAILABLE)
                .collect(Collectors.toList());
    }

    // Checkout Book
    public synchronized BookLending checkoutBook(String libraryId, String memberId, String bookItemBarcode) {
        if (libraryId == null || memberId == null || bookItemBarcode == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Library library = libraries.get(libraryId);
        if (library == null) {
            throw new IllegalArgumentException("Library not found");
        }

        Member member = members.get(memberId);
        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }

        BookItem bookItem = library.getBookItemByBarcode(bookItemBarcode);
        if (bookItem == null) {
            throw new IllegalArgumentException("Book item not found");
        }

        // Check if book is available
        if (bookItem.getBookItemStatus() != BookItemStatus.AVAILABLE) {
            throw new IllegalStateException("Book is not available for checkout");
        }

        // Check if member has reached checkout limit
        if (member.getTotalBooksCheckedOut() >= Constants.MAX_BOOKS_PER_MEMBER) {
            throw new IllegalStateException("Member has reached maximum checkout limit");
        }

        // Check if member already has this book
        if (activeLendings.values().stream()
                .anyMatch(lending -> lending.getMemberId().equals(memberId) 
                        && lending.getBookItemBarcode().equals(bookItemBarcode))) {
            throw new IllegalStateException("Member already has this book checked out");
        }

        return lendingService.checkoutBook(library, member, bookItem, activeLendings, notificationService);
    }

    // Return Book
    public synchronized Fine returnBook(String libraryId, String memberId, String bookItemBarcode) {
        if (libraryId == null || memberId == null || bookItemBarcode == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Library library = libraries.get(libraryId);
        if (library == null) {
            throw new IllegalArgumentException("Library not found");
        }

        BookLending lending = activeLendings.get(bookItemBarcode);
        if (lending == null || !lending.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("Book lending not found or doesn't belong to member");
        }

        BookItem bookItem = library.getBookItemByBarcode(bookItemBarcode);
        if (bookItem == null) {
            throw new IllegalArgumentException("Book item not found");
        }

        Fine fine = lendingService.returnBook(library, memberId, lending, bookItem, activeLendings, notificationService);
        
        // Update member checkout count
        Member member = members.get(memberId);
        if (member != null) {
            member.setTotalBooksCheckedOut(Math.max(0, member.getTotalBooksCheckedOut() - 1));
            
            // Send notification
            if (fine.getAmount() > 0) {
                notificationService.sendNotification(member, NotificationType.OTHER,
                        "Book returned. Fine amount: $" + fine.getAmount());
            } else {
                notificationService.sendNotification(member, NotificationType.OTHER,
                        "Book returned successfully");
            }
        }
        
        return fine;
    }

    // Renew Book
    public synchronized boolean renewBook(String libraryId, String memberId, String bookItemBarcode) {
        if (libraryId == null || memberId == null || bookItemBarcode == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        BookLending lending = activeLendings.get(bookItemBarcode);
        if (lending == null || !lending.getMemberId().equals(memberId)) {
            throw new IllegalArgumentException("Book lending not found");
        }

        // Check if book is overdue
        if (LocalDateTime.now().isAfter(lending.getDueDate())) {
            throw new IllegalStateException("Cannot renew overdue book. Please pay fine first");
        }

        // Extend due date
        lending.setDueDate(LocalDateTime.now().plusDays(Constants.DEFAULT_RETURN_DAYS));
        return true;
    }

    // Get active lendings for a member
    public List<BookLending> getMemberLendings(String memberId) {
        return activeLendings.values().stream()
                .filter(lending -> lending.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }

    // Get all active lendings
    public List<BookLending> getAllActiveLendings() {
        return new ArrayList<>(activeLendings.values());
    }
}
