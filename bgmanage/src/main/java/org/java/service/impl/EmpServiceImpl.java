package org.java.service.impl;

import org.java.dao.EmpMapper;
import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public Map<String, Object> empLogin(String username, String password) {
        return empMapper.empLogin(username,password);
    }

    @Override
    public String empCheck(String uname) {
        return empMapper.empCheck(uname);
    }
}
