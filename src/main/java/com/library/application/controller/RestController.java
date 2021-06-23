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
@RequestMapping(value = "/user")
public class RestController {
    Environment env;
    UserService userService;

    @Autowired
    public RestController(Environment env, UserService service) {
        this.env = env;
        this.userService = service;
    }

    @GetMapping(value = "/check")
    public String checkking() {
        return "OK!!!!!!!!!";
    }

    //회원가입요청
    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RequestUser user) throws JSONException {
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
    //로그인 요청
    @PostMapping(value = "/login")
    public ResponseEntity<ResponseToken> login(@RequestBody HashMap<String,Object> requestLogin) throws JSONException {
        //Login 유효성 검사후 Token 발급
        String Token = String.valueOf(Optional.ofNullable(userService.login(requestLogin))
                .orElseThrow(() -> new UserLoginErrorException("ID&PW를 다시확인해주세요!")));

        // status = OK , bearer 토큰 응답 ==> JS에서 sessionStorage 저장
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseToken(Token,"bearer"));
    }
}
