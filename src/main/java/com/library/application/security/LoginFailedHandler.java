package com.library.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.application.ResponseVo.ResponseData;
import com.library.application.ResponseVo.ResponseDataStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;

public class LoginFailedHandler implements AuthenticationFailureHandler {
    Environment env;
    @Autowired
    public LoginFailedHandler(Environment env) {
        this.env = env;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        String userId = request.getParameter("userId");
        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용
        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseDataStatus.ERROR);
        responseData.setStatus(ResponseDataStatus.ERROR);
        HashMap<String,Object> items = new HashMap<>();
        items.put("url" , "/");
        items.put("userId",userId);
        //오류별 메세징 처리
        if(e instanceof BadCredentialsException){
            items.put("meg","아이디와 비밀번호를 확인해 주세요");
        }else if(e instanceof SessionAuthenticationException){
            items.put("meg","현재 로그인 중인 아이디 입니다.");
        }
        else if(e.getMessage().contains("Role")){
            items.put("meg","존재하지 않는 아이디 입니다.");
        }
        if(userId.trim().length()==0){
            items.replace("meg","아이디와 비밀번호를 입력해주세요");
        }

        responseData.setItem(items);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(mapper.writeValueAsString(responseData));
        response.getWriter().flush();
    }
}
