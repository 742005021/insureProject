package org.java.web;

import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String insert(@RequestParam Map<String,Object> map){
        System.out.println(map);
        return "redirect:/index/main";
    }
}
