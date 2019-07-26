package org.java.service;

import java.util.List;
import java.util.Map;

public interface Case_ReportService {

    public int insert(Map<String,Object> map);

    public List<Map<String,Object>> getList_ByStatu(int statu);

    public Map<String,Object> getReport_ById(String cr_id);

    Map<String,Object> getReport_ByEntrustId(String task_id);

    int policy_check(String policy_no);

    Map<String,Object> getPolicyImg(String policy_id);
}
