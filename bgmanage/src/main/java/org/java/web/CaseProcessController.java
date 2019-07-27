package org.java.web;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.catalina.Server;
import org.java.service.CaseProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CaseProcessController {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CaseProcessService caseService;

    @Autowired
    private RepositoryService repositoryService;

    //当前是 哪一个阶段 的审核
    @GetMapping("audit/{taskId}/{caseId}/{taskDefKey}")
    public String showOrderDetail(Model model, @PathVariable("taskId") String taskId, @PathVariable("caseId") String caseId, @PathVariable("taskDefKey") String taskDefKey){

        //把这个三参数，保存在model
        model.addAttribute("taskId",taskId);
        model.addAttribute("orderId",caseId) ;
        model.addAttribute("taskDefKey",taskDefKey);//标识 哪一个阶段 审核

        //根据采购单的id，找到该采购单的详细业务数据，显示在页面，方便工作人员审核
        //Map<String,Object> map = caseService.showOrderDetail(orderId);

        //model.addAttribute("m", map);//存储该采购单详情

        return "";
    }


    //高亮显示流程图
    @GetMapping("showActiveMap/{instanceId}")
    public String showActiveMap(@PathVariable("instanceId") String instanceId,Model model){

        //根据流程实例id（instanceId）得到对应的流程实例
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery();
        query.processInstanceId(instanceId);//设置要查询的实例id
        ProcessInstance instance = query.singleResult();//得到一个流程实例

        //根据流程部署id，得到该流程实例的流程定义
        String processDefinitionId = instance.getProcessDefinitionId();//得到流程定义id
        //如果要通过流程定义，得到当前流程图的某一个任务节点，需要使用到ProcessDefinition的子类ProcessDefinitionEntity
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);


        //通过实例，获得，当前流程已经执行到哪一个阶段的节点id
        String activityId =instance.getActivityId();
        //通过activtyId从ProcessDefinitionEntity取到某任务的节点
        ActivityImpl act =processDefinition.findActivity(activityId);
        //分别取到当前任务节点的坐标参数:x,y,width,height
        model.addAttribute("x", act.getX());
        model.addAttribute("y", act.getY());
        model.addAttribute("width", act.getWidth());
        model.addAttribute("height", act.getHeight());

        //得到流程部署id,与png文件的名称
        String deploymentId = processDefinition.getDeploymentId();//部署id
        String png = processDefinition.getDiagramResourceName();//png文件的名称

        //将部署id,png文件的名称，存储在model中
        model.addAttribute("deploymentId", deploymentId);
        model.addAttribute("png",png);
        model.addAttribute("tstyle","border-radius:12px;position:absolute;left:"+(act.getX()-2)+"px;top:"+(act.getY())+"px;width:"+(act.getWidth())+"px;height:"+(act.getHeight()-10)+"px;border:3px red solid;");

        return "/processDefinition/showActiveMap";
    }

    //查询所有正在审核中的报案单
    @GetMapping("/getAll")
    public String getAll(Model model){
        List<Map<String,Object>> list=caseService.showProcessInstance();
        model.addAttribute("list",list);
        return "/processDefinition/showProcessInstance";
    }

    //根据流程实例ID删除报案单
    @GetMapping("delProcessInstance/{instanceId}")
    public String delProcessInstance(@PathVariable("instanceId") String instanceId){
        runtimeService.deleteProcessInstance(instanceId,"instanceId");
        return "redirect:/getAll";
    }

    //返回某一个流程实例，所经历的所有任务阶段
    @GetMapping("showHistoryTask/{instanceId}")
    public String showHistroyTask(@PathVariable("instanceId") String instanceId,Model model){
        List<HistoricTaskInstance> list=caseService.showHistoryTask(instanceId);
        model.addAttribute("list",list);
        return "/processDefinition/showHistoryTask";
    }

    //查询历史流程
    @GetMapping("historyTaskList")
    public String historyTaskList(HttpSession session, Integer page, Integer limit, HttpServletRequest request) {
        String user=(String) session.getAttribute("user");
        System.err.println(user+"---------");
        Map<String, Object> m = new HashMap<>();
        System.err.println("11111"+caseService.historyTaskList(user,page,limit).size());
        List<HistoricTaskInstance> data=caseService.historyTaskList(user,page,limit);
        request.setAttribute("htr",data);
//        m.put("count", data.size());
//        m.put("data", data);
//        m.put("code", 0);
//        m.put("msg", "");
        return "/processDefinition/showTaskList";

    }
}
