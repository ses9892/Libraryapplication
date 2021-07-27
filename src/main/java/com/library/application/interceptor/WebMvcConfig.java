package com.library.application.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//인터셉터
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    //토근을 복호화하고 유저의 토큰이맞는지 확인하는 클래스의 인스턴스
    private final BearerAuthInterceptor bearerAuthInterceptor;
    private  String uploadImgesPath;
    private  String uploadPdfPath;
    private final Environment env;

    @Autowired
    public WebMvcConfig(BearerAuthInterceptor bearerAuthInterceptor, @Value("${custom.path.upload-imges}") String uploadImgesPath,
                        @Value("${custom.path.upload-pdf}")String uploadPdfPath,Environment env) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
        this.uploadImgesPath=uploadImgesPath;
        this.uploadPdfPath = uploadPdfPath;
        this.env=env;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/check/book")
        .addPathPatterns("/library/book/*/lend")
        .addPathPatterns("/library/save")
        .addPathPatterns("/library/return")
        .addPathPatterns("/library/book/*")
        .addPathPatterns("/library/book/favorites/**")
        .addPathPatterns("/user-service/pwdCheck")
        .addPathPatterns("/chat/setnick")
        .addPathPatterns("/user-service/user/info").addPathPatterns("/user-service/user/auto_return")
        .addPathPatterns("/user-service/mypage");
        //  user/login = 로그인 요청을 받으면 요청메소드를 처리전에 인터셉터에서 걸어둔 필터를 한번 거친다.
    }

        //인터셉터로 pdf 경로도 만들어보자!!
    /** Resource Handler
     *  PDF , IMG , text 수정 할것*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name").toLowerCase();
        if(os.equals("windows 10")){
        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:/"+uploadImgesPath);
        registry
                .addResourceHandler("/pdf/**")
                .addResourceLocations("file:/"+uploadPdfPath);
        registry
                .addResourceHandler("/resource/**")
                .addResourceLocations("/img/**");
        // img/filename , pdf/pdfFileName
        }else{ LinuxChange(registry); }
    }
    public void LinuxChange(ResourceHandlerRegistry registry){

        registry
                .addResourceHandler("/img/**")
                .addResourceLocations("file:/home/ec2-user/Library-Data/img/");
        registry
                .addResourceHandler("/pdf/**")
                .addResourceLocations("file:/home/ec2-user/Library-Data/pdf/");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
        multipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024*100); // 파일당 업로드 크기 제한 (5MB)
        return multipartResolver;
    }
}
