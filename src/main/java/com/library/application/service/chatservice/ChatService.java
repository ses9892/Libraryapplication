package com.library.application.service.chatservice;

import com.library.application.dto.RoomDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.List;
// 채팅 방 생성 & 입장 & 퇴장에 대한 기능을 수행하는 메소드를 구현한 인터페이스
public interface ChatService {

    Boolean createRoom(HashMap<String, Object> params);

    List<RoomDto> selectRoomList();

    boolean enterRoom(HashMap<String, Object> hmap);

    RoomDto selectRoomByRoomName(String roomName);

    boolean exitSessionId(WebSocketSession session);
}
