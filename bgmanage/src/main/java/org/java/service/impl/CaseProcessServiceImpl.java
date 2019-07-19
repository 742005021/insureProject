package org.java.service.impl;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.java.dao.CaseProcessMapper;
import org.java.service.CaseProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CaseProcessServiceImpl implements CaseProcessService {

    @Autowired
    private CaseProcessMapper caseProcessMapper;

    @Autowired
    private RuntimeService runtimeService;


    public Map<String, Object> showCaseDetail(String cr_id) {
        return caseProcessMapper.showCaseDetail(cr_id);
    }


    public List<Map<String, Object>> showPersonTask(String user) {
        return null;
    }


    public void submitCase(String taskId) {

    }


    public void submitAudit(Map<String, Object> m) {

    }


    public List<Map<String, Object>> showProcessInstance() {
        return null;
    }


    public List<HistoricTaskInstance> showHistoryTask(String instanceId) {
        return null;
    }


    public Map<String, Object> findOfficeById(String taskId) {
        return null;
    }

    //查询所有流程实例
    public Map<String, Object> getAll() {
        return caseProcessMapper.getAll();
    }
}
