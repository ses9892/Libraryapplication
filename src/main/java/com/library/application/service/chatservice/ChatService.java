package com.library.application.service.chatservice;

import com.library.application.dto.RoomDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;

public interface ChatService {
    Boolean createRoom(HashMap<String, Object> params);

    List<RoomDto> selectRoomList();


    boolean enterRoom(HashMap<String, Object> hmap);

    RoomDto selectRoomByRoomName(String roomName);

    boolean exitSessionId(WebSocketSession session);
}
