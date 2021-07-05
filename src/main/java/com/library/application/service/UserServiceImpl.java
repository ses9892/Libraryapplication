package com.library.application.service;

import com.library.application.dto.UserDto;
import com.library.application.exception.UserLoginErrorException;
import com.library.application.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@org.springframework.stereotype.Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    Environment env;

    @Override
    public void save(String test) {
        userMapper.save(test);
    }
    //회원가입
    @Override
    public Boolean register(UserDto user) {
        //user password 암호화 해서 꼭 저장하기!
        try{
        userMapper.register(user);
        }catch (Exception e ){
            return false;
        }
        return true;
    }
    //Login 요청 -> 검사 -> 토큰발급
    @Override
    public String login(HashMap<String, Object> requestLogin) {
        UserDto userDto = userMapper.findByUserId(requestLogin);
        //로그인 실패시 null 리턴
        if(userDto==null){      // 회원정보가 없을시
            return null;
        }else if(!userDto.getPwd().trim().equals(requestLogin.get("password"))){    //비밀번호 일치하지 않을경우
            return null;
        }
        //로그인 성공시 토큰 발급
       String Token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();
        return Token;
    }

    @Override
    public String duplication(String idCheck) {
        // idCheck의 중복갯수
        int Cnt = userMapper.duplication(idCheck);
        if(Cnt ==0){
            return "true";
        }
        return "false";
    }

    @Override
    public UserDto pwdCheck(HashMap<String, Object> hmap) {
        UserDto dto = userMapper.selectByUserIdAndPwd(hmap);
        return dto;
    }

    @Override
    public UserDto selectUserId(String userId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId);
        UserDto userDto = userMapper.findByUserId(hashMap);
        return userDto;
    }

    @Override
    public Boolean updateUser(UserDto userDto) {
        HashMap hashMap = new HashMap();
        hashMap.put("userId", userDto.getUserId());
        UserDto dto = Optional.ofNullable(userMapper.findByUserId(hashMap))
                .orElseThrow(()-> new UserLoginErrorException("존재하지 않는 회원입니다."));
        userMapper.updateUserDate(userDto);
        return true;
    }
}
