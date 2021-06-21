package com.library.application.controller;


import com.library.application.ResponseVo.RequestUser;
import com.library.application.dto.UserDto;
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

import javax.validation.Valid;

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

//    @PostMapping(value = "/save")
//    public String save(@RequestBody User user) {
//        log.info(env.getProperty("server.port")
//        );
//        service.save(user.getTest());
//        return "save ok";
//    }
    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@Valid @RequestBody RequestUser user) throws JSONException {
        UserDto userDto = new ModelMapper().map(user,UserDto.class);
        Boolean registerCheck = userService.register(userDto);
        if(registerCheck==false){
            throw new UserRegisterErrorException("회원가입실패");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("meg","회원가입완료!");

        return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject.toString());
    }
}
