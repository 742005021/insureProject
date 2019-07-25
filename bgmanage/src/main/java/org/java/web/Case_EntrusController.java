package org.java.web;

import org.java.service.Case_EntrusService;
import org.java.service.Case_ReportService;
import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class Case_EntrusController {

    @Autowired
    private EmpService empService;
    @Autowired
    private Case_ReportService case_reportService;
    @Autowired
    private Case_EntrusService case_entrusService;

    @GetMapping("toCase_Entrus/{report_id}")
    public String toCase_Entrus(@PathVariable("report_id") String report_id, Model model){
        model.addAttribute("emps",empService.getAllSurvey_Emp());
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        return "Case_Entrus";
    }

    @PostMapping("Case_Entrus/submit")
    public String submit(@RequestParam Map<String,Object> map, Model model){
        int n=case_entrusService.insert(map);
        model.addAttribute("path","/report/list?statu=0");
        if (n==1){
            model.addAttribute("msg","委托完成！");
        }else{
            model.addAttribute("msg","委托失败！");
        }
        return "massage";
    }
}
