package com.library.application.service;

import com.library.application.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.util.HashMap;

public interface UserService extends UserDetailsService {
    public void save(String test);

    Boolean register(UserDto user);

    String login(HashMap<String, Object> requestLogin);

    String duplication(String idCheck);

    UserDto pwdCheck(HashMap<String, Object> hmap);

    UserDto selectUserId(String userId);

    Boolean updateUser(UserDto userDto);

    void deleteUser(String userId);

    String emailSend(String email) throws MessagingException;

    String forgetPwd(HashMap<String, Object> data) throws MessagingException;
}
