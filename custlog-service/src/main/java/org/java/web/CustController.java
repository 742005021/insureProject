package org.java.web;

import org.java.service.CustService;
import org.java.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class CustController {

    @Autowired
    private CustService service;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private  RedisTemplate<Object,Object> objectTemplate;

    @Autowired
    private MessageService messageService;

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession ses, Model model){

        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);

        Map<String,Object> map=service.custLogin(username, password);

        if (map==null){
            model.addAttribute("msg", "用户名或密码错误");
            return "/login";
        }
        String custid= (String) map.get("cust_id");
        int score=service.getCustScore(custid);
        map.put("score", score);
        ses.setAttribute("cust", map);

       redisTemplate.opsForValue().set("custid", custid, 20, TimeUnit.MINUTES);


        return "/index";
    }


    @RequestMapping(value = {"index","toindex"})
    public String index(HttpSession ses){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> cache = (Map<String, Object>) objectTemplate.opsForHash().get("cust", "map");
        if (cache != null){
            map.putAll(cache);
            String custid= (String) cache.get("cust_id");
            int score=service.getCustScore(custid);
            map.put("score", score);
            ses.setAttribute("cust", map);
        }
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
        Map<String,Object> m=new HashMap<>();
        String uname= (String) map.get("uname");
        String custid=service.check(uname);
        m.put("custid", custid);
        m.put("title", uname+"恭喜您注册成功");
        m.put("content", "恭喜您注册成功,送您100积分,可以在您的个人中心查看");
        m.put("date", new Date());
       int n1= messageService.addMessage(m);
        if (n==1&&n1==1){

            return "login";
        }else{
            return "register";
        }

    }

    @RequestMapping("editpwd/{pwd}")
    @ResponseBody
    public String editpwd(@PathVariable("pwd")String pwd,HttpSession ses){
        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String cust_id= (String) cust.get("cust_id");
      int n=  service.editPwd(pwd, cust_id);
        if (n==1){
            return "true";
        }else {return "false";}



    }

}
