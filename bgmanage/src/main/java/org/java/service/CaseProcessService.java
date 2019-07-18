package org.java.service;

import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CaseProcessService {
    public Map<String,Object> showCaseDetail(String cr_id);//根据订单id，显示该订单的详情
    public List<Map<String, Object>> showPersonTask(String user);//查询个人待办任务
    public void submitCase(String  taskId);//提交采购订单
    public void submitAudit(Map<String,Object> m);//提交审核意见
    public List<Map<String,Object>> showProcessInstance();//查看正在审核中的流程实例
    public List<HistoricTaskInstance> showHistoryTask(String instanceId);//返回某一个流程实例，所经历的所有任务阶段
    public Map<String, Object> findOfficeById(String taskId);//通过实例Id查询流程实例
    public Map<String,Object> getAll(); //查询所有流程实例
}
