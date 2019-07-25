package org.java.web;

import org.java.service.Case_ReportService;
import org.java.service.Survey_ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class VerifyController {

    @Autowired
    private Survey_ResultService survey_resultService;

    @Autowired
    private Case_ReportService case_reportService;

    @RequestMapping("verify/list")  //审核列表
    public String to_verifys(@RequestParam int statu, Model model){
        model.addAttribute("statu",statu);
        model.addAttribute("path","/verify/list");
        model.addAttribute("list",survey_resultService.getList(statu));
        return "/verify/survey_result_list";
    }

    @RequestMapping("surevy_Result/{entrust_id}")//勘查结果
    public String surevy_Result(@PathVariable ("entrust_id") String entrust_id,Model model){
        model.addAttribute("result",survey_resultService.getResult(entrust_id));
        model.addAttribute("case_report",case_reportService.getReport_ByEntrustId(entrust_id));
        return "survey/result";
    }

    @RequestMapping("to_Verify/{entrust_id}")   //审核页
    public String to_Verify(@PathVariable ("entrust_id") String entrust_id,Model model){
        model.addAttribute("result",survey_resultService.getResult(entrust_id));
        model.addAttribute("case_report",case_reportService.getReport_ByEntrustId(entrust_id));
        return "/verify/verify";
    }

    @RequestMapping("submit_Verify")
    public String submit_Verify(@RequestParam Map<String,Object> map){  //提交审核
        survey_resultService.submitVerify(map);
        return "redirect:/verify/list?statu=0";
    }
}
