package org.java.web;

import org.java.service.Case_ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.util.Map;

@Controller
public class Case_ReportController {

    @Autowired
    private Case_ReportService case_reportService;

    @PostMapping("report/insert")
    public String insert(@RequestParam(value = "deathcertificate",required = false) MultipartFile deathcertificate,@RequestParam Map<String,Object> map, Model model) throws Exception {
        if (deathcertificate!=null){
            byte[] bytes = FileCopyUtils.copyToByteArray(deathcertificate.getInputStream());
            Blob blob=new SerialBlob(bytes);
            map.put("deathcertificate",blob);
        }
        if(map.get("autopsy")==null){
            map.put("auto", "false");
        }

        int n=case_reportService.insert(map);
        model.addAttribute("msg",n==1?"申请已提交!":"提交失败!");
        model.addAttribute("path","/index/case_report");
        return "massage";
    }

    @RequestMapping("report/list")
    public String list(@RequestParam int statu,Model model){
        model.addAttribute("statu",statu);
        model.addAttribute("list",case_reportService.getList_ByStatu(statu));
        return "case_report_list";
    }

    @RequestMapping("report_detailed/{report_id}")
    public String report_detailed(@PathVariable("report_id") String report_id, Model model){
        model.addAttribute("case_report",case_reportService.getReport_ById(report_id));
        return "case_report_detailed";
    }

    @PostMapping("report_policy_check/{policy_no}")
    @ResponseBody
    public Object policy_check(@PathVariable("policy_no") String policy_no){
        return case_reportService.policy_check(policy_no);
    }

    @RequestMapping("show_PolicyImg/{policy_Id}")
    public void show_PolicyImg(@PathVariable("policy_Id") String policy_Id, HttpServletResponse res)throws  Exception{
        Map<String,Object> map = case_reportService.getPolicyImg(policy_Id);

        byte[] data=(byte[])map.get("pdf");

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

    @RequestMapping("deathcertificate/{cr_id}")
    public void show_deathcertificate(@PathVariable("cr_id") String cr_id,HttpServletResponse res)throws Exception{

        Map<String,Object> map = case_reportService.getDeathcertificate(cr_id);

        byte[] data=(byte[])map.get("file");

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
}
