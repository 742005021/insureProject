package org.java.web;

import org.java.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @RequestMapping("showPolicy")
    public String showPolicy(Model model, HttpSession ses){

        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");

        //保障中
        List<Map<String, Object>> list1 = policyService.getPolicy(custid, "6");
        int count1=list1.size();

        //待支付

        List<Map<String, Object>> list2 = policyService.getPolicy(custid, "3");
            int count2 =list2.size();


        //全部

        List<Map<String, Object>> list3 = policyService.getPolicy(custid, "%");
        int count3 =list3.size();
        Map<String,Object> map=new HashMap<>();
        map.put("list1", list1);
        map.put("count1", count1);
        map.put("list2", list2);
        map.put("count2", count2);
        map.put("list3", list3);
        map.put("count3", count3);
        //System.out.println(map);
        model.addAttribute("map", map);

        return "custop/mypolicy";


    }
}
