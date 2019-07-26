package org.java.service;

import java.util.List;
import java.util.Map;

public interface Exit_policyService {

    List<Map<String,Object>> getExitPolicy();
    Map<String,Object> getOnePolicy( int eid);


    List<Map<String,Object>> getPersonPolicy(int statu);


    int updateStatu( int eid);

}
