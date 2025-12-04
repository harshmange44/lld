package org.hrsh.librarymgmt;

public class Book {
    private String isbn;
    private String title;
    private Category category;
    private Author author;
    private BookType bookType;
    private int noOfPages;

    public Book(String isbn, String title, Category category, Author author, BookType bookType, int noOfPages) {
        this.isbn = isbn;
        this.title = title;
        this.category = category;
        this.author = author;
        this.bookType = bookType;
        this.noOfPages = noOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public int getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(int noOfPages) {
        this.noOfPages = noOfPages;
    }
}
