package com.library.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//User에 맞는 기능을 제공하는 요청을 받는 컨트롤러
@Controller
@RequestMapping(value = "/user-service/")
public class UserServiceController {


    @GetMapping(value = "/mypage")
    public String myPage(){
        return "user-service/mypage";
    }
}
