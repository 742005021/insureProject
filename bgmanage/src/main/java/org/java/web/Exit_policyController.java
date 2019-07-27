package org.java.web;

import org.java.service.Exit_policyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Exit_policyController {

    @Autowired
    private Exit_policyService exit_policyService;

    @RequestMapping("/showExitPolicy/{statu}")
    public String showExitPolicy(Model model,@PathVariable("statu") int statu){

        List<Map<String, Object>> list = exit_policyService.getPersonPolicy(statu);
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

    @RequestMapping("/commitPolicy")
    public String commitPolicy(@RequestParam Map<String,Object> map, HttpSession ses,Model model) throws Exception{

        Map<String,Object> emp= (Map<String, Object>) ses.getAttribute("emp_account");


        String order_id= (String) map.get("policyid");

        int emp_id=Integer.parseInt(emp.get("emp_id").toString());

        int type=Integer.parseInt(emp.get("type").toString());

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        double price =Double.parseDouble(map.get("price").toString());
        Date starttime=  format.parse(map.get("stime").toString());
        Date createtime=  format.parse(map.get("createtime").toString());
        long time = (createtime.getTime() - starttime.getTime()) / (24 * 60 * 60 * 1000);

        if (time>10){
            price*=0.4;
        }else if (time<=10){
            price*=0.8;
        }


        Map<String,Object> info=new HashMap<>();
        info.put("eid", map.get("eid"));
        info.put("emp_id",emp_id );
        info.put("audit", map.get("message"));
       int n= exit_policyService.updateStatu(Integer.parseInt(map.get("eid").toString()));

        Runnable runnable = () -> exit_policyService.addAudit(info);
        new Thread(runnable).start();


        Map<String,Object> msg=new HashMap<>();

        String s = String.format("%.2f", price);
        msg.put("custid", map.get("custid"));
        msg.put("title", "您的保费退还成功");
        msg.put("content", "您的保费"+s+"元退还成功,已打入您的银行卡,请您在个人中心查收");
        msg.put("date", new Date());





        if (type==7){
            model.addAttribute("msg", "审核提交成功");
            model.addAttribute("path","/showPersonPolicy/1" );
            return "massage";
        }else if (type==9){
            model.addAttribute("msg", "审核提交成功");
            model.addAttribute("path","/showPersonPolicy/3" );

            exit_policyService.addMessage(msg);
            Runnable runnable2 = () -> exit_policyService.updateOrderDate(order_id);
            new Thread(runnable2).start();

            return "massage";

        }else {
            return "";
        }

    }


}
