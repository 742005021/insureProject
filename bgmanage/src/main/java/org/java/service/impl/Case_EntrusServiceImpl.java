package org.java.service.impl;

import org.java.dao.Case_EntrustMapper;
import org.java.dao.Eventsurvey_TaskMapper;
import org.java.dao.Peoplesurvey_TaskMapper;
import org.java.dao.Sitesurvey_TaskMapper;
import org.java.service.Case_EntrusService;
import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Case_EntrusServiceImpl implements Case_EntrusService {

    @Autowired
    private Case_EntrustMapper case_entrustMapper;
    @Autowired
    private Sitesurvey_TaskMapper sitesurvey_taskMapper;
    @Autowired
    private Peoplesurvey_TaskMapper peoplesurvey_taskMapper;
    @Autowired
    private Eventsurvey_TaskMapper eventsurvey_taskMapper;

    @Override
    public int submit(Map<String, Object> map) {
        return 0;
    }
}
