package org.java.service.impl;

import org.java.dao.Sitesurvey_TaskMapper;
import org.java.service.Sitesurvey_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Sitesurvey_TaskServiceImpl implements Sitesurvey_TaskService {

    @Autowired
    private Sitesurvey_TaskMapper sitesurvey_taskMapper;

    @Override
    public List<Map<String, Object>> getTasks(Integer emp_id, Integer statu) {
        return sitesurvey_taskMapper.getTasks(emp_id, statu);
    }

    @Override
    public Map<String, Object> getTaskById(String task_id) {
        return sitesurvey_taskMapper.getTaskById(task_id);
    }

    @Override
    public int update(Map<String, Object> map) {
        return sitesurvey_taskMapper.update(map);
    }
}
