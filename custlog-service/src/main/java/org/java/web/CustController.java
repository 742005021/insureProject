package org.java.web;

import org.java.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class CustController {

    @Autowired
    private CustService service;

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession ses, Model model){

        Map<String,Object> map=service.custLogin(username, password);
        if (map==null){
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }

        ses.setAttribute("cust", map);


        return "/index";
    }



}
