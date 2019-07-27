package org.java.web;

import org.java.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Map;

@Controller
public class SurevyController {

    @Autowired
    private Eventsurvey_TaskService eventsurvey_taskService;

    @Autowired
    private Peoplesurvey_TaskService peoplesurvey_taskService;

    @Autowired
    private Sitesurvey_TaskService sitesurvey_taskService;

    @Autowired
    private Case_ReportService case_reportService;

    @Autowired
    private Case_EntrusService case_entrusService;

    @Autowired
    private Survey_ResultService survey_resultService;

    @RequestMapping("event_tasks")
    public String toEvent_Tasks(@RequestParam int statu,HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("type",1);
        model.addAttribute("path","/event_tasks");
        model.addAttribute("statu",statu);
        model.addAttribute("tasks",eventsurvey_taskService.getTasks(id,statu));
        return "/survey/task_list";
    }

    @RequestMapping("people_tasks")
    public String toPeoplesurvey_Tasks(@RequestParam int statu,HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("type",2);
        model.addAttribute("path","/people_tasks");
        model.addAttribute("statu",statu);
        model.addAttribute("tasks",peoplesurvey_taskService.getTasks(id,statu));
        return "/survey/task_list";
    }

    @RequestMapping("site_tasks")
    public String toSitesurvey_Tasks(@RequestParam int statu,HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("type",3);
        model.addAttribute("path","/site_tasks");
        model.addAttribute("statu",statu);
        model.addAttribute("tasks",sitesurvey_taskService.getTasks(id,statu));
        return "/survey/task_list";
    }

    @RequestMapping("event_task/{report_id}/{task_id}")
    public String toEvent_task(@PathVariable("report_id") String report_id,@PathVariable("task_id") String task_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        model.addAttribute("task",eventsurvey_taskService.getTaskById(task_id));
        return "/survey/event";
    }

    @RequestMapping("people_task/{report_id}/{task_id}")
    public String toPeople_task(@PathVariable("report_id") String report_id,@PathVariable("task_id") String task_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        model.addAttribute("task",peoplesurvey_taskService.getTaskById(task_id));
        return "/survey/people";
    }

    @RequestMapping("site_task/{report_id}/{task_id}")
    public String toSite_task(@PathVariable("report_id") String report_id,@PathVariable("task_id") String task_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        model.addAttribute("task",sitesurvey_taskService.getTaskById(task_id));
        return "/survey/site";
    }

    @PostMapping("submitEvent_task")
    public String submitEvent_task(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam Map<String,Object> map)throws Exception{
        if (file!=null){
            byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
            Blob blob=new SerialBlob(bytes);
            map.put("file",blob);
        }

        if(map.get("massage")==null){
            map.put("massage","");
        }
        int n=eventsurvey_taskService.update(map);
        String task_id=map.get("task_id").toString();
        if(n==1 && case_entrusService.checkTaskStatu(task_id)){//检查三项勘查任务是否都完成
            survey_resultService.insert(task_id);   //根据任务编号，添加勘查结果
        }
        return "redirect:/event_tasks?statu=0";
    }

    @PostMapping("submitPeople_task")
    public String submitPeople_task(@RequestParam(value = "file",required = false) MultipartFile file,@RequestParam Map<String,Object> map)throws Exception{
        if (file!=null){
            byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
            Blob blob=new SerialBlob(bytes);
            map.put("file",blob);
        }

        if(map.get("massage")==null){
            map.put("massage","");
        }
        int n=peoplesurvey_taskService.update(map);
        String task_id=map.get("task_id").toString();
        if(n==1 && case_entrusService.checkTaskStatu(task_id)){//检查三项勘查任务是否都完成
            survey_resultService.insert(task_id);   //根据任务编号，添加勘查结果
        }
        return "redirect:/people_tasks?statu=0";
    }

    @PostMapping("submitSite_task")
    public String submitSite_task(@RequestParam(value = "file",required = false) MultipartFile file, @RequestParam Map<String, Object> map)throws Exception{
        if (file!=null){
            byte[] bytes = FileCopyUtils.copyToByteArray(file.getInputStream());
            Blob blob=new SerialBlob(bytes);
            map.put("file",blob);
        }

        if(map.get("massage")==null){
            map.put("massage","");
        }
        int n=sitesurvey_taskService.update(map);
        String task_id=map.get("task_id").toString();
        if(n==1 && case_entrusService.checkTaskStatu(task_id)){//检查三项勘查任务是否都完成
            survey_resultService.insert(task_id);   //根据任务编号，添加勘查结果
        }
        return "redirect:/site_tasks?statu=0";
    }
}
