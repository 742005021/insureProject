package org.java.service.impl;

import org.java.dao.ExitpolicyMapper;
import org.java.service.ExitpolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ExitpolicyServiceImpl implements ExitpolicyService {

    @Autowired
    private ExitpolicyMapper exitpolicyMapper;

    @Override
    public int createExitpolicy(Map<String, Object> map) {
        return exitpolicyMapper.createExitpolicy(map);
    }
}
