package org.hrsh.librarymgmt;

import java.time.LocalDateTime;

public class BookItem extends Book {
    private String barcode;
    private boolean isReferenceOnly;
    private BookItemStatus bookItemStatus;
    private double price;
    private LocalDateTime purchasedAt;
    private LocalDateTime publishedAt;
    private Rack placedAt;

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isReferenceOnly() {
        return isReferenceOnly;
    }

    public void setReferenceOnly(boolean referenceOnly) {
        isReferenceOnly = referenceOnly;
    }

    public BookItemStatus getBookItemStatus() {
        return bookItemStatus;
    }

    public void setBookItemStatus(BookItemStatus bookItemStatus) {
        this.bookItemStatus = bookItemStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(LocalDateTime purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Rack getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Rack placedAt) {
        this.placedAt = placedAt;
    }
}
