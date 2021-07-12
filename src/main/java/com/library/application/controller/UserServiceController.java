package com.library.application.controller;

import com.library.application.ResponseVo.RequestUser;
import com.library.application.dto.UserDto;
import com.library.application.exception.UserLoginErrorException;
import com.library.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Optional;

//User에 맞는 기능을 제공하는 요청을 받는 컨트롤러
@Controller
@RequestMapping(value = "/user-service/")
@Slf4j
public class UserServiceController {

    @Autowired
    UserService userService;

    @Autowired
    Environment env;

    //마이페이지 이동
    //현재 seesionStorage에만 userId가 저장되있기때문에 추후 mypage 구성할때 userId로 받아와야할 무언가을 개발 해야함
    @GetMapping(value = "/mypage")
    public String myPage(){
        return "user-service/mypage";
    }

    //정보수정 -> 패스워드 선 체크 페이지 이동
    //회원탈퇴 ->  ''
    @GetMapping(value = "/info")
    public  String infoCheck(HttpServletRequest request,Model model){
        model.addAttribute("flag",request.getParameter("flag"));
        return "user-service/pwdCheck";
    }
    //정보조회
    @GetMapping(value = "/info/{userId}")
    public String info(@PathVariable("userId") String userId, Model model) {
        UserDto userDto = userService.selectUserId(userId);
        model.addAttribute("user",userDto);
        return "user-service/info";
    }
    //정보조회 -> 수정
    //수정이필요함 alert() 라던지 확인창 이 아직없음
    @PostMapping(value = "/user/{userId}")
    public String infoChange(@PathVariable("userId") String userId, @ModelAttribute RequestUser user) {
        UserDto userDto = new ModelMapper().map(user,UserDto.class);
        //변환 -> 유저정보수정
        Boolean result =  userService.updateUser(userDto);
        return "redirect:/user-service/mypage";
    }
    @ResponseBody
    @DeleteMapping(value = "/user/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "정상적으로 탈퇴처리가 완료 되었습니다.";
    }
    //패스워드 체크 - > 정보조회
    //패스워드 체크 - > 회원탈퇴
    @ResponseBody
    @GetMapping(value = "/pwdCheck")
    public ResponseEntity<String> pwdCheck(HttpServletRequest request, @RequestParam("pwd") String pwd,@RequestParam("flag")String flag) {
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",request.getAttribute("userId"));
        hmap.put("pwd",pwd);
        UserDto userDto = Optional.ofNullable(userService.pwdCheck(hmap))
                .orElseThrow(()->new UserLoginErrorException("비밀번호가 일치하지 않습니다."));
        String url =null;
        switch (flag){
            case "infoChange":
                url = env.getProperty("custom.location.userInfo") + userDto.getUserId();
                break;
            case "userDelete":
                url = env.getProperty("custom.location.userDelete") + userDto.getUserId();
                break;
            case "autoReturn":
                url = env.getProperty("custom.location.autoReturn") + userDto.getUserId();
        }
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }
    //자동반납설정 페이지 이동
    @GetMapping(value = "/user/auto_return/{userId}")
    public String autoReturnChange(@PathVariable("userId") String userId , Model model){
        UserDto userDto = userService.selectUserId(userId);
        model.addAttribute("user",userDto);
        return "user-service/autoReturnChange";
    }
    //대출받은 도서 조회




}
