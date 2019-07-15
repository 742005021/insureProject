package org.java.service.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.java.dao.Case_ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Case_ReportService implements org.java.service.Case_ReportService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private Case_ReportMapper mapper;

    @Override
    public int insert(Map<String, Object> map) {
        //创建申请单id 通过businessKey关联activit
        String cr_id = UuidUtil.getTimeBasedUuid().toString();

        System.out.println("cr_id"+cr_id);
        //创建流程实例
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("claim",cr_id);

        map.put("cr_id",cr_id);
        map.put("process_instance_id",instance.getProcessDefinitionId() );

        return mapper.insert(map);
    }
}
