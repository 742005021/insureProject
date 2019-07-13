package org.java.web;

import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.xml.ws.Dispatch;
import java.util.Map;

@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @PostMapping("login")
    public String login(String username, String password, HttpSession ses, Model model){

        Map<String,Object> map=empService.empLogin(username, password);
        if (map==null){
            model.addAttribute("msg", "用户名或密码错误!");
            return "/index";
        }
        ses.setAttribute("emp_account",map);
        return "/processDefinition/deploy";
    }

    @RequestMapping("index/{pagename}")
    public String toPage(@PathVariable("pagename") String pagename){
        System.out.println(pagename);
        return "/"+pagename;
    }
}
