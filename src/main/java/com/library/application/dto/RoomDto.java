package com.library.application.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoomDto {
    private int roomNumber;
    private String roomName;
    private String ref_userId;
    private int allowedNum;
    private int enterNum;
    private String session_id1;
    private String session_id2;
}
