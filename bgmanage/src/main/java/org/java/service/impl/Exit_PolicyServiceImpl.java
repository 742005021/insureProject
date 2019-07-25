package org.java.service.impl;

import org.java.dao.Exit_PolicyMapper;
import org.java.service.Exit_policyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class Exit_PolicyServiceImpl implements Exit_policyService {

    @Autowired
    private Exit_PolicyMapper exit_policyMapper;
    @Override
    public List<Map<String, Object>> getExitPolicy() {
        return exit_policyMapper.getExitPolicy();
    }
}
