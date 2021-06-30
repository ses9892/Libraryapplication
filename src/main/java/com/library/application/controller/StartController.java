package com.library.application.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import org.aspectj.apache.bcel.util.ClassPath;
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 

@Controller
@Slf4j
public class StartController {
	private static final Logger logger = LoggerFactory.getLogger(StartController.class);

	@Autowired 
	MessageSource messageSource;

    @RequestMapping(value = "/")
    public String home(Locale locale, HttpServletRequest request, Model model,@RequestParam(required = false)String lang) {
    	HttpSession s = request.getSession();
    	s.setAttribute("lang", lang);
    	System.out.println(s.getAttribute("lang"));
    	System.out.println(ClassPath.getClassPath());
    	logger.info("Welcome i18n! The client locale is {}.", locale); // localeResolver 로부터 Locale 을 출력해 봅니다.
    	logger.info("site.title : {}", messageSource.getMessage("site.title", null, "default text", locale));
    	logger.info("site.count : {}", messageSource.getMessage("site.count", new String[] {"첫번째"}, "default text", locale)); 
    	logger.info("not.exist : {}", messageSource.getMessage("not.exist", null, "default text", locale)); 
    	//logger.info("not.exist 기본값 없음 : {}", messageSource.getMessage("not.exist", null, locale));
        // RequestMapingHandler로 부터 받은 Locale 객체를 출력해 봅니다.

        model.addAttribute("clientLocale", locale);

        log.info("tq");
        return "login";
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
    public String Home(){

        return "home";
    }
    @RequestMapping(value = "/register")
    public String register() { ;
        return "register";
    }

}
