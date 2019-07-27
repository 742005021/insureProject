package org.java.service;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface Exit_policyService {

    List<Map<String,Object>> getExitPolicy();
    Map<String,Object> getOnePolicy( int eid);


    List<Map<String,Object>> getPersonPolicy(int statu);


    int updateStatu( int eid);


    int addAudit(Map<String, Object> map);
    int addMessage(Map<String,Object> map);

    int updateOrderDate(String orderid);

}
