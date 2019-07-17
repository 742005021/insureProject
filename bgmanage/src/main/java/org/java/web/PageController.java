package org.java.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("index/{pagename}")
    public String toPage(@PathVariable("pagename") String pagename){
        return "/"+pagename;
    }

    @RequestMapping("processDefinition/{pagename}")
    public String toPrcessDefinition(@PathVariable("pagename") String pagename){
        return "/processDefinition/"+pagename;
    }

}
