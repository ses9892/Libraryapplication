package com.library.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class HomeController {

    @RequestMapping(value = "/")
    public String home() {
        log.info("tq");
        return "login";
    }
    @RequestMapping(value = "/library")
    public String Home(){

        return "home";
    }
    @RequestMapping(value = "/register")
    public String register() { ;
        return "register";
    }

}
