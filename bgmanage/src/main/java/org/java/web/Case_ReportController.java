package org.java.web;

import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class Case_ReportController {

    @Autowired
    private Case_ReportService case_reportService;

    @PostMapping("report/insert")
    public String insert(@RequestParam Map<String,Object> map,Model model){
        int n=case_reportService.insert(map);
        model.addAttribute("msg",n==1?"申请已提交!":"提交失败!");
        return "massage";
    }

    @RequestMapping("report/list")
    public String list(@RequestParam int statu,Model model){
        model.addAttribute("report_statu",statu);
        model.addAttribute("list",case_reportService.getList_ByStatu(statu));
        return "case_report_list";
    }

    @RequestMapping("report_detailed/{report_id}")
    public String report_detailed(@PathVariable("report_id") String report_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        return "case_report_detailed";
    }

}
