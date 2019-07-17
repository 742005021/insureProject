package org.java.service;

import java.util.List;
import java.util.Map;

public interface PolicyService {

    List<Map<String,Object>> getPolicy(String custid,  String statu);
}
