package com.library.application.service;

import com.library.application.dto.UserDto;
import com.library.application.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
@Transactional
public class ServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void save(String test) {
        userMapper.save(test);
    }

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
}
