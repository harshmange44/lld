package org.hrsh.linkedin;

import java.util.List;

public interface Search {
    <T> List<T> searchRecords(SearchIndex searchIndex, String query);
}