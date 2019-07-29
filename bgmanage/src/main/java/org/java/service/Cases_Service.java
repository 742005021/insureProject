package org.java.service;

import java.util.List;
import java.util.Map;

public interface Cases_Service {

    List<Map<String,Object>> getList(Integer statu);

    Map<String,Object> getCasesByLiable_emp(Integer emp_id);

    int letme(Map<String,Object> map);

    int nextStep(Map<String,Object> map);

    int complete(Map<String, Object> map);

    int insertFinance(Map<String,Object> map);

    int getMyCasesCount(Integer emp_id);

    List<Map<String,Object>> getFinances(int statu);

    int financeComplete(int id);

    int getFinanceStatu(int id);

    int addMessage(int id,String title,String content);
}
