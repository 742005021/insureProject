package org.java.web;

import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("report")
public class Case_ReportController {

    @Autowired
    private Case_ReportService service;

    @PostMapping("insert")
    public String insert(@RequestParam Map<String,Object> map,Model model){
        int n=service.insert(map);
        model.addAttribute("msg",n==1?"申请已提交!":"提交失败!");
        return "massage";
    }

    @RequestMapping("list")
    public String list(@RequestParam int statu,Model model){
        model.addAttribute("report_statu",statu);
        model.addAttribute("list",service.getList_ByStatu(statu));
        return "case_report_list";
    }
}
