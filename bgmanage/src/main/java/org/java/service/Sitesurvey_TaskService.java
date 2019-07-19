package org.java.service;

import java.util.List;
import java.util.Map;

public interface Sitesurvey_TaskService {
    List<Map<String,Object>> getTasks(Integer emp_id, Integer statu);

    Map<String,Object> getTaskById(String task_id);

    int update(Map<String,Object> map);
}

