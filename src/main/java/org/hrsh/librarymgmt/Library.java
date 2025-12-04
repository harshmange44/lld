package org.hrsh.librarymgmt;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.UUID;

public class Library {
    private String id;
    private String name;
    private Location location;
    private List<Book> books;
    private List<Librarian> librarians;
    private Map<String, List<BookItem>> isbnBookItemMap; // ISBN -> List of BookItems
    private Map<String, BookItem> barcodeBookItemMap; // barcode -> BookItem

    public Library(String name, Location location) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.location = location;
        this.books = new CopyOnWriteArrayList<>();
        this.librarians = new CopyOnWriteArrayList<>();
        this.isbnBookItemMap = new ConcurrentHashMap<>();
        this.barcodeBookItemMap = new ConcurrentHashMap<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
        isbnBookItemMap.putIfAbsent(book.getIsbn(), new CopyOnWriteArrayList<>());
    }

    public void addBookItem(BookItem bookItem) {
        if (bookItem == null) {
            throw new IllegalArgumentException("BookItem cannot be null");
        }
        String isbn = bookItem.getIsbn();
        isbnBookItemMap.computeIfAbsent(isbn, k -> new CopyOnWriteArrayList<>()).add(bookItem);
        barcodeBookItemMap.put(bookItem.getBarcode(), bookItem);
    }

    public BookItem getBookItemByBarcode(String barcode) {
        return barcodeBookItemMap.get(barcode);
    }

    public List<BookItem> getBookItems() {
        return barcodeBookItemMap.values().stream()
                .collect(Collectors.toList());
    }

    public List<BookItem> getBookItemsByIsbn(String isbn) {
        return isbnBookItemMap.getOrDefault(isbn, Collections.emptyList());
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void setBooks(List<Book> books) {
        this.books = books != null ? new CopyOnWriteArrayList<>(books) : new CopyOnWriteArrayList<>();
    }

    public List<Librarian> getLibrarians() {
        return new ArrayList<>(librarians);
    }

    public void setLibrarians(List<Librarian> librarians) {
        this.librarians = librarians != null ? new CopyOnWriteArrayList<>(librarians) : new CopyOnWriteArrayList<>();
    }
}
