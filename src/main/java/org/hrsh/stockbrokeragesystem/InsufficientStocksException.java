package org.hrsh.stockbrokeragesystem;

public class InsufficientStocksException extends RuntimeException {
    public InsufficientStocksException(String message) {
        super(message);
    }
}
