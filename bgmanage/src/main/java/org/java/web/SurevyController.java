package org.java.web;

import org.java.service.Eventsurvey_TaskService;
import org.java.service.Peoplesurvey_TaskService;
import org.java.service.Sitesurvey_TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("event_task")
    public String toEvent_Task(HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("tasks",eventsurvey_taskService.getTasks(id,0));
        return "/survey/task_list";
    }

    @RequestMapping("people_task")
    public String toPeoplesurvey_Task(HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("tasks",eventsurvey_taskService.getTasks(id,0));
        return "/survey/task_list";
    }

    @RequestMapping("site_task")
    public String toSitesurvey_Task(HttpSession ses, Model model){
        Map<String,Object> emp_account = (Map<String, Object>) ses.getAttribute("emp_account");
        Integer id= (Integer) emp_account.get("emp_id");
        model.addAttribute("tasks",eventsurvey_taskService.getTasks(id,0));
        return "/survey/task_list";
    }
}
