package org.java.service;

import java.util.List;
import java.util.Map;

public interface EmpService {

    Map<String,Object> empLogin(String username, String password);

    String empCheck(String uname);

    //获取所有勘查员姓名和id
    List<Map<String,Object>> getAllSurvey_Emp();
}
