package org.java.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {

    @RequestMapping("/buy/{page}")
    public String redirectPage(@PathVariable("page") String page){
        return page;
    }

}
