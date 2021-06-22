package com.library.application.interceptor;

import com.library.application.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
@Slf4j
public class BearerAuthInterceptor implements HandlerInterceptor {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public BearerAuthInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HandlerInterceptor의 메소드이며, 인터셉터로 해당 메소드의 재정의한 기능이 수행된다.
        log.info("preHandle Start ( JWT Token Valid)");
        String Token = request.getHeader("Authorization");
        log.info(Token);
        Token = Token.replace("bearer","");
        if(Token==null){
            return false;
        }
        if(!jwtTokenProvider.validateToken(Token)){
            throw new IllegalArgumentException("유효하지 않는 사용자입니다.");
        }

        return true;
    }
}
