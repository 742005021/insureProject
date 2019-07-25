package org.java.service;

import java.util.List;
import java.util.Map;

public interface Cases_Service {

    List<Map<String,Object>> getList(Integer statu);

    Map<String,Object> getCasesByLiable_emp(Integer emp_id);

    int letme(Map<String,Object> map);

    int nextStep(Map<String,Object> map);
}
