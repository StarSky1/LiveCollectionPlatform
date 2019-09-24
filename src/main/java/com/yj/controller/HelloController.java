package com.yj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "index";
    }
}
