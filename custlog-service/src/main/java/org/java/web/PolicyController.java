package org.java.web;

import org.java.service.PolicyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PolicyController {

    @Autowired
    private PolicyOrderService policyOrderService;

    @RequestMapping("showPolicy")
    public String showPolicy(Model model, HttpSession ses){

        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");

        //保障中
        List<Map<String, Object>> list1 = policyOrderService.getPolicy(custid, "6");
        int count1=list1.size();

        //待支付

        List<Map<String, Object>> list2 = policyOrderService.getPolicy(custid, "3");
            int count2 =list2.size();


        //全部

        List<Map<String, Object>> list3 = policyOrderService.getPolicy(custid, "%");
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

    @RequestMapping("/showPersonPolicy/{policyid}")
    @ResponseBody
    public void showPerson(@PathVariable("policyid")String policyid, HttpServletResponse res) throws Exception{
       Map<String,Object> policy = policyOrderService.getPolicyOrder(policyid);
        byte[] data = (byte[]) policy.get("policyorder");
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        OutputStream out = res.getOutputStream();
        byte[] b = new byte[8192];
        while ((len = in.read(b, 0, 8192)) != -1) {
            out.write(b, 0, len);
        }

        out.close();
        in.close();
    }



    @RequestMapping("/exitpay/{policyid}")
    public String exitPay(@PathVariable("policyid")String policyid){
        policyOrderService.exitPay(policyid);
        return "redirect:/showPolicy";

    }


}
