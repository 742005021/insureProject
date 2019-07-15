package org.java.service.impl;

import org.java.dao.EmpMapper;
import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Override
    public Map<String, Object> empLogin(String username, String password) {
        int n=empMapper.updateLogintime(username,password);
        System.out.println("员工号:"+username+(n==1?",登录时间已更新！":",用户名或密码错误！"));
        return empMapper.empLogin(username,password);
    }

    @Override
    public String empCheck(String uname) {
        return empMapper.empCheck(uname);
    }

    @Override
    public List<Map<String, Object>> getAllSurvey_Emp() {
        return empMapper.getAllSurvey_Emp();
    }
}
