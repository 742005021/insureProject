package org.java.service;

import java.util.List;
import java.util.Map;

public interface PolicyOrderService {

    List<Map<String,Object>> getPolicy(String custid,  String statu);

    Map<String,Object> getPolicyOrder(String policyid);
}
