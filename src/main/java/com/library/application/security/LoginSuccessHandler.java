package com.library.application.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.application.ResponseVo.ResponseData;
import com.library.application.ResponseVo.ResponseDataStatus;
import com.library.application.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    Environment env;
    @Autowired
    public LoginSuccessHandler(Environment env) {
        this.env = env;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String Token = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();
        //토큰 response

        ObjectMapper mapper = new ObjectMapper();	//JSON 변경용

        ResponseData responseData = new ResponseData();
        responseData.setCode(ResponseDataStatus.SUCCESS);
        responseData.setStatus(ResponseDataStatus.SUCCESS);
        HashMap<String,Object> items = new HashMap<>();
        items.put("url" , "/library?lang=kr");
        items.put("meg","반가워요^^");
        items.put("token",Token);
        responseData.setItem(items);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(mapper.writeValueAsString(responseData));
        response.getWriter().flush();

    }
}
