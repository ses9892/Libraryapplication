package com.library.application.service;

import com.library.application.dto.UserDto;

public interface UserService {
    public void save(String test);

    Boolean register(UserDto user);
}
