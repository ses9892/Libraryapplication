package com.library.application.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//인터셉터
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor().addPathPatterns("/user/login");
        //  user/login = 로그인 요청을 받으면 요청메소드를 처리전에 인터셉터에서 걸어둔 필터를 한번 거친다.

    }
}
