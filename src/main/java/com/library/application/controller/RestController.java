package com.library.application.controller;


import com.library.application.ResponseVo.RequestUser;
import com.library.application.ResponseVo.ResponseToken;
import com.library.application.dto.UserDto;
import com.library.application.exception.UserLoginErrorException;
import com.library.application.exception.UserRegisterErrorException;
import com.library.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController {
    Environment env;
    UserService userService;

    @Autowired
    public RestController(Environment env, UserService service) {
        this.env = env;
        this.userService = service;
    }

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
    @PostMapping(value = "/duplication")
    public String duplication(@RequestBody HashMap<String,Object> userId){
        String idCheck = (String) userId.get("userId");
        String result = userService.duplication(idCheck);
        return result;
    }

}
