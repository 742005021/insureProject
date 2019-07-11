package org.java.web;

import org.java.service.CustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        ses.setAttribute("emp_account", map);
        return "/index";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession ses){
        ses.removeAttribute("cust");
        return "/login";
    }

    @RequestMapping("check/{uname}")
    @ResponseBody
    public String check(@PathVariable("uname") String uname){

        String cust_id=service.check(uname);
        if (cust_id==null){
            return "true";
        }


        return "false";
    }

    @RequestMapping("register")
    public String register(@RequestParam Map<String,Object> map){
        int n=service.addCustAccount(map);
        if (n==1){
            return "login";
        }else{
            return "register";
        }

    }

}
