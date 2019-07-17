package org.java.service;

import java.util.List;
import java.util.Map;

public interface LoadResourcesService {
    List<Map<String, Object>> loadInsureType();

    List<Map<String, Object>> loadInsureItem();

    Object searchTerms(Integer id);

    Map<String, Object> searchInsureInfo(Integer item_id);

    List<Map<String, Object>> loadJobs();

    List<Map<String, Object>> loadProfession(Integer job_id);
}
