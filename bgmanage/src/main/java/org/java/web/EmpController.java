package org.java.web;

import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Dispatch;
import java.util.Map;

@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @RequestMapping("login")
    public String login(String username, String password, HttpSession ses, Model model){
        if(username==null){
            return "/index";
        }

        Map<String,Object> map=empService.empLogin(username, password);
        if (map==null){
            model.addAttribute("msg", "用户名或密码错误!");
            return "/index";
        }
        ses.setAttribute("emp_account",map);
        return "redirect:index/main";
//        return "/processDefinition/deploy";
    }

    @GetMapping("exit")
    public String exit(HttpSession ses){
        ses.removeAttribute("emp_account");
        return "redirect:/login";
    }
}
