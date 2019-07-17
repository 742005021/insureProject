package org.java.service.impl;

import org.java.dao.Eventsurvey_TaskMapper;
import org.java.service.Eventsurvey_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Eventsurvey_TaskServiceImpl implements Eventsurvey_TaskService {

    @Autowired
    private Eventsurvey_TaskMapper eventsurvey_taskMapper;

    @Override
    public List<Map<String, Object>> getTasks(Integer emp_id, Integer statu) {
        return eventsurvey_taskMapper.getTasks(emp_id,statu);
    }
}
