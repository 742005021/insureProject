package org.java.web;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
public class ProcessDefinitionController {
    @Autowired
    private RepositoryService repositoryService;

    @GetMapping("forward/{folder}/{page}")
    public String  forward(@PathVariable("folder") String folder,@PathVariable("page") String page){
        return "/"+folder+"/"+page;
    }

    /**
     * 部署流程定义
     * @return
     */
    @PostMapping("deployment")
    public String deployment(@RequestParam("bpmn") MultipartFile bpmn,@RequestParam("png") MultipartFile png) throws IOException {

        //获得上传的文件名称
        String bpmnName = bpmn.getOriginalFilename();
        String pngName = png.getOriginalFilename();

        //把上传的文件，转换成输入流
        InputStream bpmnIn = bpmn.getInputStream();
        InputStream pngIn = png.getInputStream();
        System.err.println(bpmnIn+"----"+pngIn);
        //部署
        repositoryService.createDeployment().addInputStream(bpmnName,bpmnIn ).addInputStream(pngName,pngIn ).deploy();
        System.out.println("部署成功！");
        return "redirect:/main";
    }

    /**
     * 显示所有的流程定义
     * @return
     */
//    @GetMapping("showProcessDefinition")
//    public String showProcessDefinition(Model model){
//        System.out.println("##############################################################");
//        ProcessDefinitionQuery  query = repositoryService.createProcessDefinitionQuery();
//        List<ProcessDefinition> list = query.list();
//        model.addAttribute("list",list );
//        return "processDefinition/showProcess";
//
//    }
}
