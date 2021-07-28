package com.library.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

// 로그인  & 회원기입 페이지 이동 로그인완료후 요청 URL 대한 컨트롤러
@Controller
@Slf4j
public class StartController {

    @Autowired
    MessageSource messageSource;

    @RequestMapping(value = "/")
    public String home() {
        log.info("tq");
        return "login";
    }

    @GetMapping("/pdfview")
    public String viewTest(){
        return "viewer";
    }

    @RequestMapping(value = "/preview")
    public void pdfStreamHandler(HttpServletResponse response) {

        File file = new File("D:\\library\\장진호_포트폴리오.pdf");
        if (file.exists()) {
            byte[] data = null;
            FileInputStream input=null;
            try {
                input= new FileInputStream(file);
                data = new byte[input.available()];
                input.read(data);
                response.getOutputStream().write(data);
            } catch (Exception e) {
                System.out.println("pdf에러 : " + e);
            }finally{
                try {
                    if(input!=null){
                        input.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequestMapping(value = "/library")
    public String Home(Locale locale , HttpServletRequest request , Model model){
        model.addAttribute("clientLocale",locale);
        return "library/home";
    }

    @RequestMapping(value = "/register")
    public String register() { ;
        return "register";
    }
}
