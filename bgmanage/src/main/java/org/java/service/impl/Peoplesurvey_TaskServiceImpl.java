package org.java.service.impl;

import org.java.dao.Peoplesurvey_TaskMapper;
import org.java.service.Peoplesurvey_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Peoplesurvey_TaskServiceImpl implements Peoplesurvey_TaskService {

    @Autowired
    private Peoplesurvey_TaskMapper peoplesurvey_taskMapper;

    @Override
    public List<Map<String, Object>> getTasks(Integer emp_id, Integer statu) {
        return peoplesurvey_taskMapper.getTasks(emp_id,statu);
    }
}
