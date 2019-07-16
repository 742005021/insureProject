package org.java.service.impl;

import org.java.dao.LoadResourcesMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LoadResourcesServiceImpl implements LoadResourcesService {

    @Autowired
    private LoadResourcesMapper mapper;

    @Override
    public List<Map<String, Object>> loadInsureType() {
        return mapper.loadInsureType();
    }

    @Override
    public List<Map<String, Object>> loadInsureItem() {
        return mapper.loadInsureItem();
    }

    @Override
    public Object searchTerms(Integer id) {
        return mapper.searchTerms(id);
    }

    @Override
    public Map<String, Object> searchInsureInfo(Integer item_id) {
        return mapper.searchInsureInfo(item_id);
    }

    @Override
    public List<Map<String, Object>> loadJobs() {
        return mapper.loadJobs();
    }

    @Override
    public List<Map<String, Object>> loadProfession(Integer job_id) {
        return mapper.loadProfession(job_id);
    }

}
