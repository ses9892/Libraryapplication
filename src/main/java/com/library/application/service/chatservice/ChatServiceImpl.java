package com.library.application.service.chatservice;

import com.library.application.dto.RoomDto;
import com.library.application.mapper.ChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    ChatMapper chatMapper;

    @Override
    public Boolean createRoom(HashMap<String, Object> params) {
        String roomName = (String) params.get("roomName");

        if(roomName!=null & !roomName.trim().equals("")){
            //해당 유저의 채팅방이 있나없나 검사
            int check = chatMapper.selectByRoomName(roomName);
            if(check>0){
                return false;
            }
            RoomDto room = new RoomDto();
            room.setRoomName(roomName);
            chatMapper.createRoom(room);
        }
        return true;
    }

    @Override
    public List<RoomDto> selectRoomList() {

        List<RoomDto> list = chatMapper.selectRoomList();

        return list;
    }

    @Override
    public boolean enterRoom(HashMap<String, Object> hmap) {
        String seesion_id = (String) hmap.get("session_id");

        RoomDto dto = chatMapper.selectRoomByRoomName(hmap);
        //인원초과방지
        if(dto.getEnterNum()==dto.getAllowedNum()){
            return false;
        }else{
            dto.setEnterNum(dto.getEnterNum()+1);
        }
        //이미 해당세션의 사용자가 또 접속을 할경우 (세션해제가안되고 또접속할경우)
        try {
        if(dto.getSession_id1().equals(seesion_id) || dto.getSession_id2().equals(seesion_id)){
            return false;
        }
        }catch (NullPointerException e){
        }
        //세션방 선택
        if(dto.getSession_id1()==null && dto.getSession_id2()==null){
            dto.setSession_id1((String) hmap.get("session_id"));
        }else if(dto.getSession_id1()!=null && dto.getSession_id2()==null){
            dto.setSession_id2((String) hmap.get("session_id"));
        }else if(dto.getSession_id1()==null && dto.getSession_id2()!=null){
            dto.setSession_id1((String) hmap.get("session_id"));
        }
        chatMapper.enterRoom(dto);
        return true;
    }

    @Override
    public RoomDto selectRoomByRoomName(String roomName) {
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("roomName",roomName);
        RoomDto dto = chatMapper.selectRoomByRoomName(hmap);
        return dto;
    }

    //채팅방 유저 퇴장메소드
    @Override
    public boolean exitSessionId(WebSocketSession session) {
     HashMap<String,Object> hmap = new HashMap<>();
     hmap.put("sessionId",session.getId());
     RoomDto dto =chatMapper.selectBySessionId(hmap);
     if(dto==null){
        return false;
     }
     if(dto.getSession_id1().matches(session.getId())){
         dto.setSession_id1(null);
     }else if(dto.getSession_id2().matches(session.getId())){
         dto.setSession_id2(null);
     }
     if(dto.getEnterNum()-1<0){
         return false;
     }
     dto.setEnterNum(dto.getEnterNum()-1);
     chatMapper.enterRoom(dto);
     return true;
    }

// RoomName을 넘겨받고 DB에 룸을 저장

}
