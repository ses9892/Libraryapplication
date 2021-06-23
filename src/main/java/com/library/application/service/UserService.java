package com.library.application.service;

import com.library.application.dto.UserDto;

import java.util.HashMap;

public interface UserService {
    public void save(String test);

    Boolean register(UserDto user);

    String login(HashMap<String, Object> requestLogin);

    String duplication(String idCheck);
}
