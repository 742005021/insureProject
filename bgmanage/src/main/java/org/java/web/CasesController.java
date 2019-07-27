package org.java.web;

import org.java.service.Cases_Service;
import org.java.service.PolicyService;
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
    
    @Autowired
    private PolicyService policyService;

    @RequestMapping("cases/list")
    public String toCases_List(@RequestParam int statu, Model model){
        model.addAttribute("statu",statu);
        model.addAttribute("list",cases_service.getList(statu));
        return "compensation/cases_list";
    }

    @RequestMapping("cases/myCases")
    public String toCases_MyCases(HttpSession ses, Model model){
        Map<String,Object> emp= (Map<String, Object>) ses.getAttribute("emp_account");
        Integer emp_id= (Integer)emp.get("emp_id");

        Map<String,Object> cases= cases_service.getCasesByLiable_emp(emp_id);
        if(cases==null){//没有任务
            model.addAttribute("msg","暂未受理案件!");
            return "compensation/cases_me";
        }
        //空值判断
        if(cases.get("deathcertificate")==null){
            cases.put("deathcertificate","");
        }
        if(cases.get("disability_level")==null){
            cases.put("disability_level","");
        }

        int step=Integer.parseInt(cases.get("step").toString());
        switch (step){
            case 1:
            case 2:
                //获取保单信息
                String s = policyService.getPolicy_Info(cases.get("policy_id").toString());
                Map<String,Object> policy_Info= getStringToMap(s.substring(1,s.length()-1));
                model.addAttribute("policy_Info",policy_Info);
                break;
            default: break;
        }

        model.addAttribute("cases",cases);
        return "compensation/cases_me";
    }

    @PostMapping("cases/nextStep")
    public String nextStep(@RequestParam Map<String,Object> map,Model model){
        int step= Integer.parseInt(map.get("step").toString());
        if(step==4){
            cases_service.complete(map);
            model.addAttribute("msg","案件已处理!");
            return "compensation/cases_me";
        }else{
            cases_service.nextStep(map);
        }
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

    public static Map<String,Object> getStringToMap(String str){
        //根据逗号截取字符串数组
        String[] str1 = str.split(",");
        //创建Map对象
        Map<String,Object> map = new HashMap<String,Object>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split("=");
            //去除键的空格
            String key=str2[0].trim();
            //str2[0]为KEY,str2[1]为值
            map.put(key,str2[1]);
        }
        return map;
    }


}
