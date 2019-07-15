package org.java.web;

import org.java.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CouponController {

    @Autowired
    private CouponService service;


    @RequestMapping("/showCoupon")
    public String showCoupon(HttpSession ses, Model model){

        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");

        //可用
        List<Map<String, Object>> list1 = service.findUsable(custid, 1);

            int count1=list1.size();



        //已用
        List<Map<String, Object>> list2 = service.findUsable(custid, 2);
        int count2=list2.size();
        //已过期
        List<Map<String, Object>> list3 = service.findUsable(custid, 3);

        int count3=list3.size();

        Map<String,Object> map=new HashMap<>();
        map.put("count1", count1);
        map.put("count2", count2);
        map.put("count3", count3);
        map.put("list1", list1);
        map.put("list2", list2);
        map.put("list3", list3);

        model.addAttribute("map", map);

        return "/custop/coupon";
    }
}
