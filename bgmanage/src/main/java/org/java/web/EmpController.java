package org.java.web;

import org.java.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class EmpController {

    @Autowired
    private EmpService empService;

    @PostMapping("/login")
    public String login(String username, String password, HttpSession ses, Model model){

        Map<String,Object> map=empService.empLogin(username, password);
        if (map==null){
            model.addAttribute("msg", "用户名或密码错误!");
            return "/index";
        }
        return "/main";
    }
}
