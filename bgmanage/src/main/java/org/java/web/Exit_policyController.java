package org.java.web;

import org.java.service.Exit_policyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@Controller
public class Exit_policyController {

    @Autowired
    private Exit_policyService exit_policyService;

    @RequestMapping("/showExitPolicy")
    public String showExitPolicy(Model model){

        List<Map<String, Object>> list = exit_policyService.getExitPolicy();
        model.addAttribute("list", list);
        return "/exitpolicy";

    }


    @RequestMapping("/showimg/{eid}/{type}")
    public void showimg(@PathVariable("eid") int eid, @PathVariable("type")String type, HttpServletResponse res) throws  Exception{

        Map<String, Object> onePolicy = exit_policyService.getOnePolicy(eid);

        byte[] data=null;

        if (type.equals("png")){
            data=(byte[])onePolicy.get("cardimg");
        }else if (type.equals("pdf")){
            data=(byte[])onePolicy.get("policypdf");
        }

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



    @RequestMapping("updateStatu/{eid}")
    @ResponseBody
    public String updateStatu(@PathVariable("eid") int eid){

        int n = exit_policyService.updateStatu(eid);
        if (n==1){
            return "true";
        }else{
            return "false";
        }



    }


    @RequestMapping("/showPersonPolicy/{statu}")
    public String showPersonPolicy(@PathVariable("statu") int statu,Model model){

        List<Map<String, Object>> list = exit_policyService.getPersonPolicy(statu);
        model.addAttribute("list", list);
        return "/showPersonPolicy";

    }


    @RequestMapping("/auditExitPolicy/{eid}")
    public String   auditExitPolicy(@PathVariable("eid") int eid, Model model){
        Map<String, Object>  policy = exit_policyService.getOnePolicy(eid);

        model.addAttribute("p", policy);
        return "/auditExitPolicy";

    }

}
