package com.library.application.controller;

import com.library.application.dto.RoomDto;
import com.library.application.service.chatservice.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/chat")
@Slf4j
public class ChatController {

    List<RoomDto> roomList = new ArrayList<>();

    @Autowired
    ChatService chatService;

    @RequestMapping("/")
    public String ClientRoom(){
        return "chat/userRoom";
    }

    @RequestMapping(value = "/room")
    public String room(){
        return "chat/room";
    }
    /**
     * 방생성하기
     * @param params
     * @return
     * */
    @RequestMapping(value = "/createRoom")
    @ResponseBody
    public List<RoomDto> createRoom(@RequestParam HashMap<String,Object> params){

        Boolean result = chatService.createRoom(params);
        if(!result){
            return null;
        }
        List<RoomDto>  DtoList = chatService.selectRoomList();
        return DtoList;
    }

    /**
     * 방 정보가져오기
     * @param params
     * @return
     */
    @RequestMapping("/getRoom")
    public @ResponseBody List<RoomDto> getRoom(@RequestParam HashMap<Object, Object> params){
        List<RoomDto> DtoList = chatService.selectRoomList();
        return DtoList;
    }

    /**
     * 채팅방
     * @return
     */
    @RequestMapping("/moveChating")
    public String chating(HttpServletRequest request, Model model){
        String roomName = request.getParameter("roomName");
        List<RoomDto> new_list = chatService.selectRoomList().stream()
                .filter(o->o.getRoomName().equals(roomName)).collect(Collectors.toList());
        if(new_list!=null && new_list.size()>0){
            model.addAttribute("roomName",new_list.get(0).getRoomName());
            model.addAttribute("roomNumber",new_list.get(0).getRoomNumber()); //오류가날수도? 중복이있으면
            return "chat/index";
        }
        return "redirect:room";
    }
    @ResponseBody
    @GetMapping(value = "/setnick")
    public ResponseEntity<String> setNick(HttpServletRequest request){
        String userId = ""+request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.OK).body(userId);
    }

}
