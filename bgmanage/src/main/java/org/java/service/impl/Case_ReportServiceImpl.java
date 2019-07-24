package org.java.service.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.java.dao.Case_ReportMapper;
import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Case_ReportServiceImpl implements Case_ReportService {

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
        map.put("process_instance_id",instance.getProcessInstanceId());

        return mapper.insert(map);
    }

    @Override
    public List<Map<String, Object>> getList_ByStatu(int statu) {
        return mapper.getList_ByStatu(statu);
    }

    @Override
    public Map<String, Object> getReport_ById(String cr_id) {
        return mapper.getReport_ById(cr_id);
    }

    @Override
    public Map<String, Object> getReport_ByEntrustId(String task_id) {
        return mapper.getReport_ByEntrustId(task_id);
    }

    @Override
    public int policy_check(String policy_no) {
        return mapper.policy_check(policy_no);
    }
}
