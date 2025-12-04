package org.hrsh.librarymgmt;

import java.time.LocalDateTime;
import java.util.Map;

public class BookLendingService {
    private static final double FINE_PER_DAY = 1.0; // $1 per day

    public BookLending checkoutBook(Library library, Member member, BookItem bookItem,
                                   Map<String, BookLending> activeLendings,
                                   NotificationService notificationService) {
        // Create lending record
        LocalDateTime borrowedAt = LocalDateTime.now();
        LocalDateTime dueDate = borrowedAt.plusDays(Constants.DEFAULT_RETURN_DAYS);

        BookLending lending = new BookLending(
                bookItem.getBarcode(),
                borrowedAt,
                dueDate,
                member.getId() // Fixed: Use member ID, not barcode
        );

        // Update book status
        bookItem.setBookItemStatus(BookItemStatus.BORROWED);

        // Update member checkout count
        member.setTotalBooksCheckedOut(member.getTotalBooksCheckedOut() + 1);

        // Store active lending
        activeLendings.put(bookItem.getBarcode(), lending);

        // Send notification
        notificationService.sendNotification(member, NotificationType.OTHER,
                "Book '" + bookItem.getTitle() + "' checked out. Due date: " + dueDate.toLocalDate());

        return lending;
    }

    public Fine returnBook(Library library, String memberId, BookLending lending, BookItem bookItem,
                          Map<String, BookLending> activeLendings,
                          NotificationService notificationService) {
        // Calculate fine if overdue
        Fine fine = calculateFine(lending);

        // Update book status
        bookItem.setBookItemStatus(BookItemStatus.AVAILABLE);

        // Remove from active lendings
        activeLendings.remove(bookItem.getBarcode());

        // Update member checkout count will be handled by LibraryMgmtSystem
        // Notification will be sent from LibraryMgmtSystem after member is retrieved

        return fine;
    }

    private Fine calculateFine(BookLending lending) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(lending.getDueDate()) || now.isEqual(lending.getDueDate())) {
            // No fine
            return new Fine(lending.getBookItemBarcode(), lending.getMemberId(), 0.0);
        }

        // Calculate fine
        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(lending.getDueDate(), now);
        double fineAmount = daysOverdue * FINE_PER_DAY;

        return new Fine(lending.getBookItemBarcode(), lending.getMemberId(), fineAmount);
    }
}

