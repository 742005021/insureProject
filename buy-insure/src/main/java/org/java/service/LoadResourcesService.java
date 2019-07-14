package org.java.service;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

public interface LoadResourcesService {
    List<Map<String, Object>> loadInsureType();

    List<Map<String, Object>> loadInsureItem();

    Object searchTerms(Integer id);
}
