package com.library.application.Handler;

import com.library.application.dto.RoomDto;
import com.library.application.service.chatservice.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    @Autowired
    ChatService chatService;

    private static List<HashMap<String,Object>> list = new ArrayList<>();
    //웹소켓 세션을 담는 리스트 = > 방1개에 참여한 사람들의 세션
    //리스트를 담는 리스트 = > 서버의 여러개의 방

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload      //전송되는 데이터(data)
                = message.getPayload();
        JSONObject obj = JsonToObjectParser(payload);
        String roomNum = (String) obj.get("roomNumber"); //내가입장한 방
        String roomName = (String) obj.get("roomNumber"); //내가입장한 방
//        String roomNum = obj.get("roomNumber");

        RoomDto roomData = chatService.selectRoomByRoomName(roomName);
        HashMap<String,Object> temp = new HashMap<>();

        String[] enterSessionList ={roomData.getSession_id1(),roomData.getSession_id2()};

        for (String sessId:enterSessionList) {
            for(int i=0;i<list.size();i++){
                if(list.get(i).containsKey(sessId)){
                    WebSocketSession wess = (WebSocketSession) list.get(i).get(sessId);
                    if(wess!=null){
                        try{
                            wess.sendMessage(new TextMessage(obj.toJSONString()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        //db에서 가져오고 list가 0보다크면 list [0->size] 만큼 순차적으로 몇번방이름인지 가져오고
        //내가 입장한 roomNum이 그방에 맞으면 temp에 담고 브레이크
//        if(list.size()>0){
//            for (int i=0;i<list.size();i++){
//                String roomNumber = (String) list.get(i).get("roomNumber"); //세션의 저장되있는 값을 가져온다. (몇번방인지)
//                if(roomNumber.equals(roomNum)){ //만약 내가입장한방이 list에 저장된 방과 같다면
//                    temp = list.get(i);         //해당 방번호에 존재하는 세션리스트(유저들)의 존재하는 모든값을 저장한다.
//                    break;
//                }
//            }
//
//            //내가입장한 방에 존재하는 세션들에게만 내가 보낸 메세지를 뿌린다.
//            for (String sessRoomNumber: temp.keySet()) {
//                if(sessRoomNumber.equals("roomNumber")){ //다양한 key,value중 roomNumber은 건너뛰기
//                    continue;
//                }
//                WebSocketSession wess = (WebSocketSession) temp.get(sessRoomNumber);
//                //들어가있는 세션에다가 메세지 계속 뿌려주기
//                if(wess!=null){
//                    try{
//                        wess.sendMessage(new TextMessage(obj.toJSONString()));
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
    }

    //클라이언트가 접속시 호출되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓연결
        super.afterConnectionEstablished(session);
//        list.add(session);
        boolean flag = false;
        String url = session.getUri().toString();       // 클라이언트가 /ws 어디로 접속했는지
        String roomNumber = url.split("/")[4];  //  "/chating/{roomNumber}
        String roomName = url.split("/")[4]; //  "/chating/{roomNumber}
        JSONObject obj = new JSONObject();
        String locationKind=null;
        try{
            locationKind = url.split("/")[5];
        //navber에서 실행시 현재 팝업에 채팅창이 켜져있나확인
        if(locationKind.equals("toolbar")){
            for (int i=0; i<list.size(); i++){
                if(list.get(i).get("enterUserId").equals(roomName)){
                    obj.put("type", "duplicatonSession");
                    session.sendMessage(new TextMessage(obj.toJSONString()));
                    return;
                }// 켜져있으면 type : duplicationSession 메세지 전송 -> js에서 alert처리
            }
        }
        }catch (ArrayIndexOutOfBoundsException e){
        }


        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("roomName",roomName);
        hmap.put("session_id",session.getId());
        flag = chatService.enterRoom(hmap);
        //방입장
        if(!flag){
            obj.put("type", "overflow");
            obj.put("sessionId", session.getId());
            session.sendMessage(new TextMessage(obj.toJSONString()));
            log.info(session + " 클라이언트 접속에러 (인원초과) ");
        }else{
            HashMap<String,Object> session_data = new HashMap<>();
            session_data.put(session.getId(),session);
            if(locationKind!=null) {
                if (locationKind.equals("admin")) {
                    session_data.put("enterUserId", "admin");
                }
            }else{
                session_data.put("enterUserId", roomName);
            }
            list.add(session_data);
            obj.put("type", "getId");
            obj.put("sessionId", session.getId());
            session.sendMessage(new TextMessage(obj.toJSONString()));
            log.info(session + " 클라이언트 접속 ");
            }
//        int idx = list.size();
//        if(idx>0){      //현재 생성된 방이 최소 1개이상있으면
//            for (int i=0; i<list.size();i++){
//                String rN = (String) list.get(i).get("roomNumber");
//                if(rN.equals(roomNumber)){      //방이 있나없나 조사
//                    flag= true;                 //있으면 flag = true
//                    idx= i ;                    //이게 몇번째 방인지? index상에서
//                    break;
//                }
//            }
//        }
//        if(flag){   //존재하는 방이라면 세션만추가
//            HashMap<String,Object> map = list.get(idx);
//            map.put(session.getId(),session);    //내 세션ID , 세션
//        }else{      // 최초 생성하는 방이라면 방번호와 세션추가
//            HashMap<String,Object> map = new HashMap<>();
//            map.put("roomNumber",roomNumber);
//            map.put(session.getId(),session);    //내 세션ID , 세션
//            list.add(map);
//        }
//
//        //세션등록이 끝나면  세션ID 값의 메세지를 전달.
//        JSONObject obj = new JSONObject();
//        obj.put("type", "getId");
//        obj.put("sessionId", session.getId());
//        session.sendMessage(new TextMessage(obj.toJSONString()));
//        log.info(session + " 클라이언트 접속 ");
//        }
    }

    //클라이언트가 접속 해제시 호출되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        Boolean flag =  chatService.exitSessionId(session);
        if(flag){
            if(list.size()>0){
                for (int i=0;i<list.size();i++){
                    if(list.get(i).containsKey(sessionId)){
                        list.remove(i);
                    }
                }
            }
        }
        log.info(session+" 클라이언트 접속 해제 ");
    }

    private static JSONObject JsonToObjectParser(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
