package org.java.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Case_ReportService implements org.java.service.Case_ReportService {

    @Autowired
    private Case_ReportService mapper;

    @Override
    public int insert(Map<String, Object> map) {
        return mapper.insert(map);
    }
}
