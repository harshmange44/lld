package org.hrsh.librarymgmt;

import java.util.List;
import java.util.Map;

public class Library {
    private String id;
    private String name;
    private Location location;
    private List<Book> books;
    private List<Librarian> librarians;
    private Map<String, List<BookItem>> isbnBookItemMap;
}
