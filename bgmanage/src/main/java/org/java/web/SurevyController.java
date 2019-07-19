package org.java.web;

import org.java.service.Case_ReportService;
import org.java.service.Eventsurvey_TaskService;
import org.java.service.Peoplesurvey_TaskService;
import org.java.service.Sitesurvey_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
        model.addAttribute("task",eventsurvey_taskService.getTaskById(task_id));
        return "/survey/people";
    }

    @RequestMapping("site_task/{report_id}/{task_id}")
    public String toSite_task(@PathVariable("report_id") String report_id,@PathVariable("task_id") String task_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        model.addAttribute("task",eventsurvey_taskService.getTaskById(task_id));
        return "/survey/site";
    }

    @PostMapping("submitEvent_task")
    public String submitEvent_task(@RequestParam Map<String,Object> map){
        System.out.println(map);
        sitesurvey_taskService.update(map);
        return "redirect:/event_tasks?statu=0";
    }

    @PostMapping("submitPeople_task")
    public String submitPeople_task(@RequestParam Map<String,Object> map){
        System.out.println(map);
        return "redirect:/people_tasks?statu=0";
    }

    @PostMapping("submitSite_task")
    public String submitSite_task(@RequestParam Map<String,Object> map){
        System.out.println(map);
        return "redirect:/site_tasks?statu=0";
    }
}
