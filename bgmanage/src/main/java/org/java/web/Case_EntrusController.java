package org.java.web;

import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class Case_EntrusController {

    @Autowired
    private EmpService empService;

    @GetMapping("toCase_Entrus")
    public String toCase_(Model model){
        model.addAttribute("emps",empService.getAllSurvey_Emp());
        return "Case_Entrus";
    }

    @PostMapping("Case_Entrus/submit")
    public String submit(@RequestParam Map<String,Object> map, Model model){
        System.out.println(map);
        model.addAttribute("msg","委托完成！");
        return "massage";
    }
}
