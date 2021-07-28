package com.library.application.service;

import com.library.application.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.util.HashMap;

//로그인 & 회원가입 & 정보 수정 & 삭제 에 대한 기능을 수행하는 메소드를 정의해놓은 인터페이스
public interface UserService extends UserDetailsService {

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
