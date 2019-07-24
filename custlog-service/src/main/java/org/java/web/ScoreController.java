package org.java.web;

import org.java.service.CouponService;
import org.java.service.CustService;
import org.java.service.MessageService;
import org.java.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private CustService custService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MessageService messageService;

    @RequestMapping("buyCoupon/{score}/{remark}")
    public String buyCoupon(@PathVariable("score")int score, @PathVariable("remark")String remark, HttpSession ses){

        //当前登录用户
        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");
        int oldScore=Integer.parseInt(cust.get("score").toString());

        int newScore=oldScore+score;
        cust.put("score", newScore);
        ses.setAttribute("cust", cust);

      custService.updateScore(custid, newScore);

      Map<String,Object> m=new HashMap<>();
      m.put("custid", custid);
      m.put("time", new Date());
      m.put("val", score);
      m.put("remark", remark);

     scoreService.addInfo(m);

     Map<String,Object> msgMap=new HashMap<>();
     String title="";
     String content="";

     int cid=0;
     if (score==-500){
         title="兑换5元优惠券成功";
         content="恭喜您成功兑换5元优惠券,您可以在您的个人中心查看";
         cid=1;
     }else if (score==-2000){
         title="兑换20元优惠券成功";
         content="恭喜您成功兑换20元优惠券,您可以在您的个人中心查看";
         cid=2;
     }else if (score==-7500){
         title="兑换60元优惠券成功";
         content="恭喜您成功兑换60元优惠券,您可以在您的个人中心查看";
         cid=3;
     }

        msgMap.put("custid", custid);
        msgMap.put("title", title);
        msgMap.put("content", content);
        msgMap.put("date", new Date());

        Runnable runnable = () -> messageService.addMessage(msgMap);
        new Thread(runnable).start();





      couponService.addCustCoupon(custid, cid);
        return "redirect:/showScore";
    }

    @RequestMapping("showScore")
    public String showScore(Model model,HttpSession ses){
        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");
        List<Map<String, Object>> list = scoreService.showScore(custid);
        model.addAttribute("list", list);
        model.addAttribute("count", list.size());

        return "custop/score";
    }
}
