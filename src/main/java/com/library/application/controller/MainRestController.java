package com.library.application.controller;



import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.application.ResponseVo.RequestUser;
import com.library.application.dto.UserDto;
import com.library.application.exception.UserRegisterErrorException;
import com.library.application.service.UserService;

import lombok.extern.slf4j.Slf4j;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;


@Controller
@Slf4j
public class MainRestController {
    @Autowired
    Environment env;
    @Autowired
    UserService userService;

    //회원가입요청
    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RequestUser user) throws JSONException {
        UserDto userDto = new ModelMapper().map(user,UserDto.class);
        //회원가입 요청 처리 (true,false)
        Boolean registerCheck = userService.register(userDto);
        if(registerCheck==false){
            throw new UserRegisterErrorException("회원가입실패");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("meg","회원가입완료!");
        //JSON 응답
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject.toString());
    }
    //ID 중복확인
    @ResponseBody
    @PostMapping(value = "/duplication")
    public String duplication(@RequestBody HashMap<String,Object> userId){
        String idCheck = (String) userId.get("userId");
        String result = userService.duplication(idCheck);
        return result;
    }
    @ResponseBody
    @PostMapping(value = "/email")
    public ResponseEntity<String> emailSend(@RequestBody HashMap<String,Object> data) throws MessagingException {
        String email = data.get("id")+"@"+data.get("mail");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        String key =userService.emailSend(email);
        return new ResponseEntity<String>(key,headers,HttpStatus.OK);
    }
}
