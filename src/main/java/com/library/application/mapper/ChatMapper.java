package com.library.application.mapper;

import com.library.application.dto.RoomDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface ChatMapper {
    //방생성
    void createRoom(RoomDto room);

    List<RoomDto> selectRoomList();

    int selectByRoomName(String roomName);

    void enterRoom(RoomDto dto);

    RoomDto selectRoomByRoomName(HashMap<String, Object> hmap);

    void exitSessionId(HashMap<String, Object> hmap);

    RoomDto selectBySessionId(HashMap<String, Object> hmap);
}
