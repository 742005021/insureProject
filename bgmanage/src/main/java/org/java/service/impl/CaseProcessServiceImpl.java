package org.java.service.impl;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.java.dao.CaseProcessMapper;
import org.java.service.CaseProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaseProcessServiceImpl implements CaseProcessService {

    @Autowired
    private CaseProcessMapper caseProcessMapper;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;


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

    //查询所有正在审核中的报案单
    public List<Map<String, Object>> showProcessInstance() {

        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();

        query.processDefinitionKey("claim");

        List<ProcessInstance> instanceList = query.list();



        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        for(ProcessInstance instance:instanceList){

            String instanceId = instance.getProcessInstanceId();

            Map<String, Object> m = caseProcessMapper.findByProcessInstanceId(instanceId);


            m.put("instanceId",instance.getProcessInstanceId());
            m.put("defId",instance.getProcessDefinitionId());
            m.put("activitiId",instance.getActivityId());
            list.add(m);
        }
        return list;
    }

    //返回某一个流程实例，所经历的所有任务阶段
    public List<HistoricTaskInstance> showHistoryTask(String instanceId) {
        HistoricTaskInstanceQuery query=historyService.createHistoricTaskInstanceQuery();
        query.processInstanceId(instanceId);
        List<HistoricTaskInstance> list=query.list();
        return list;
    }


    public Map<String, Object> findCaseById(String taskId) {
        return null;
    }


    public Map<String, Object> getAll() {
        return caseProcessMapper.getAll();
    }


    //查询历史流程
    public List<HistoricTaskInstance> historyTaskList(String user, Integer index, Integer limit) {
        // 历史任务Servic

        // 创建历史任务实例查询
        HistoricTaskInstanceQuery query = historyService.createHistoricTaskInstanceQuery();
        List<HistoricTaskInstance> list = query.taskAssignee(user).finished().list();// 查询已经完成的任务

        System.out.println("111111:"+list);
//        Map<String,Object> map=new HashMap<>();
//        map.put("page", (index - 1) * limit);
//        map.put("limit", limit);
        return list;
    }
}
