package org.java.web;

import org.java.service.CustService;
import org.java.service.ExitpolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import java.io.InputStream;
import java.sql.Blob;
import java.util.Date;
import java.util.Map;

@Controller
public class ExitpolicyController {

    @Autowired
    private ExitpolicyService exitpolicyService;

    @Autowired
    private CustService custService;

    @RequestMapping("/exitpolicy/{policyid}")
    public String toexitpolicy(Model model, @PathVariable("policyid") String policyid){
        model.addAttribute("policyid", policyid);
        return "custop/exitpolicy";

    }


    @RequestMapping("/commitpolicy")
    public String commitpolicy(@RequestParam Map<String,Object> map,
                               HttpSession ses,
                               @RequestParam("cardimg")MultipartFile cardimg,
                               @RequestParam("policypdf") MultipartFile policypdf) throws Exception{


        Map<String,Object> cust= (Map<String, Object>) ses.getAttribute("cust");
        String custid= (String) cust.get("cust_id");
        String custname=(String)custService.getcustinfo(custid).get("cust_name");
        InputStream cust_in=cardimg.getInputStream();
        InputStream policy_in=policypdf.getInputStream();

        byte[] bytes1 = FileCopyUtils.copyToByteArray(cust_in);
        byte[] bytes2 = FileCopyUtils.copyToByteArray(policy_in);
        Blob cust_img=new SerialBlob(bytes1);
        Blob policy_pdf=new SerialBlob(bytes2);

        map.put("custid", custid);
        map.put("cardimg", cust_img);
        map.put("policypdf", policy_pdf);
        map.put("date", new Date());


      int n=  exitpolicyService.createExitpolicy(map);

        return "/custop/success";


    }
}
