package org.java.web;

import org.apache.commons.lang.StringUtils;
import org.java.service.LinkManService;
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
public class LinkManController {
    @Autowired
    private LinkManService lservice;

    @RequestMapping("showAllLm/{custid}")
    public String getByCustid(@PathVariable("custid") String custid, Model model){


        model.addAttribute("list", lservice.getByCustId(custid));
        model.addAttribute("size", lservice.getByCustId(custid).size());
//        System.out.println(lservice.getByCustId(custid));
        return "/custop/family";

    }

    @RequestMapping("/oplinkman")
    public String oplinkman(@RequestParam Map<String,Object> map, HttpSession ses){
        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String cust_id= (String) cust.get("cust_id");
        System.out.println(cust_id);
        String insured_id=(String)map.get("insured_id");



        map.put("custid", cust_id);
        if (StringUtils.isBlank(insured_id)){
            lservice.addLinkMan(map);
        }else {
            lservice.updateLinkMan(map);
        }

        System.out.println(map);

        return "redirect:/showAllLm/"+cust_id;
    }

    @RequestMapping("dellm/{insured_id}")
    public String dellm(@PathVariable("insured_id") String insure_id,HttpSession ses){
        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String cust_id= (String) cust.get("cust_id");
        lservice.delLinkMan(insure_id);
        return "redirect:/showAllLm/"+cust_id;
    }

    @ResponseBody
    @RequestMapping("toedit/{insuredid}")
    public Map<String,Object> toupdate(@PathVariable("insuredid") String insure_id){

        return lservice.getOne(insure_id);
    }
}
