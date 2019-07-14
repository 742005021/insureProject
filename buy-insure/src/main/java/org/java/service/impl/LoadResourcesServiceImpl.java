package org.java.service.impl;

import org.java.dao.LoadResourcesMapper;
import org.java.service.LoadResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Blob;
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

}
