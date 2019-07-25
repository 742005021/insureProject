package org.java.web;

import org.java.service.Exit_policyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class Exit_policyController {

    @Autowired
    private Exit_policyService exit_policyService;

    @RequestMapping("/showExitPolicy")
    public String showExitPolicy(Model model){

        List<Map<String, Object>> list = exit_policyService.getExitPolicy();
        model.addAttribute("list", list);
        return "/exitpolicy";

    }


}
