package org.java.web;

import org.java.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping("showMessage/{custid}")
    public String showMessage(Model model, @PathVariable("custid")String custid){

        List<Map<String, Object>> list = messageService.getMessage(custid);
        model.addAttribute("list", list);
        model.addAttribute("count", list.size());

        return "custop/message";
    }

    @RequestMapping("/updateStatu/{mid}")
    @ResponseBody
    public String updateStatu(@PathVariable("mid") int mid){

        messageService.updateStatu(mid);
        return "yes";
    }



}
