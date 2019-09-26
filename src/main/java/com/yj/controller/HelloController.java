package com.yj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String indexPage(Model m){
        m.addAttribute("showSearch",true);
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(Model m){
        m.addAttribute("showSearch",true);
        return "index";
    }

    @RequestMapping("/404")
    public String error_404(){
        return "/error/404";
    }
}
