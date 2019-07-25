package org.java.web;

import org.java.service.Cases_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CasesController {

    @Autowired
    private Cases_Service cases_service;

    @RequestMapping("cases/list")
    public String toCases_List(@RequestParam int statu, Model model){
        model.addAttribute("statu",statu);
        model.addAttribute("list",cases_service.getList(statu));
        return "compensation/cases_list";
    }

    @RequestMapping("cases/myCases")
    public String toCases_MyList(HttpSession ses, Model model){
        Map<String,Object> emp= (Map<String, Object>) ses.getAttribute("emp_account");
        Integer emp_id= (Integer)emp.get("emp_id");

        Map<String,Object> cases= cases_service.getCasesByLiable_emp(emp_id);
        if(cases.get("deathcertificate")==null){
            cases.put("deathcertificate","");
        }
        model.addAttribute("cases",cases);
        return "compensation/cases_me";
    }

    @PostMapping("cases/nextStep")
    public String nextStep(@RequestParam Map<String,Object> map){
        cases_service.nextStep(map);
        return "redirect:/cases/myCases";
    }

    @RequestMapping("cases/letme")
    public String letme(@RequestParam String id, HttpSession ses, Model model){
        Map<String,Object> emp= (Map<String, Object>) ses.getAttribute("emp_account");
        Map<String,Object> map= new HashMap<String,Object>();
        map.put("emp_id",emp.get("emp_id"));
        map.put("id",id);

        int n=cases_service.letme(map);
        model.addAttribute("path","/cases/list?statu=0");
        model.addAttribute("msg",1==1?"案件已受理！":"案件受理失败！");
        return "massage";
    }
}
