package org.java.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface EmpService {

    Map<String,Object> empLogin(String username, String password);

    String empCheck(String uname);

}
