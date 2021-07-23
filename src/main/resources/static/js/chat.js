$(function (){
    var userId;
    window.onload = function(){
        crR();
        // location.href="/chat/moveChating?roomName="+userId;
    }
})
//시작시 해당 id 의 제목을가진 채팅방 생성
function crR(){
    $.ajax({
        type: "GET",
        url: "/chat/setnick",
        async: false,
        headers: {
            'content-type': 'application/json',
            'Authorization': 'bearer' + localStorage.getItem('jwt')
        },
        contentType: 'application/json; charset=utf-8'
    }).success(function (data){
        userId = data;
        createRoom();
    }).error(function (error){
        alert(error.responseJSON.message);
        location.href="/logout";
    })
    wsOpen(userId);
}
//방생성
//생성버튼 클릭 -->  방이름 -> ajax -> 메모리에 생성 -> result -> 방생성메소드 실행
function createRoom(){
    var msg = {	roomName : userId};
    commonAjax('/chat/createRoom', msg, 'post', function(result){
    });
}
function commonAjax(url, parameter, type, calbak, contentType){
    $.ajax({
        url: url,
        data: parameter,
        type: type,
        contentType : contentType!=null?contentType:'application/x-www-form-urlencoded; charset=UTF-8',
        success: function (res) {
            calbak(res);
        },
        error : function(err){
            console.log('error');
            calbak(err);
        }
    });
}

function wsOpen(userName){
    // ws = new WebSocket("ws://" + location.host + "/chating/"+$('#roomNumber').val());   //ws 로딩
    ws = new WebSocket("ws://" + location.host + "/chating/"+userId);   //ws 로딩
    wsEvt();
}

function wsEvt() {
    //onopen 소켓이 열리면 실행할 function
    ws.onopen = function(data){
    }
    //메세지가 소켓에 담겨졌을때 실행할 function
    ws.onmessage = function(data) {
        var msg = data.data;
        if(msg != null && msg.trim() != ''){
            var d= JSON.parse(msg);
            console.log(d);
            if(d.type == "getId"){      // 채팅방입장일시
                //프로젝트에서는 ajax 통신 -> 토큰값 -> subject(userId) 등록예정
                var si = d.sessionId != null ? d.sessionId : "";
                if(si != ''){
                    $("#sessionId").val(si);
                }
                $("#chating").append("<p class='me'>" +userId+": 채팅방에 입장하였습니다." + "</p>");
            }else if(d.type == "message"){      //입장후 채팅을 입력할시
                if(d.sessionId == $("#sessionId").val()){
                    $("#chating").append("<p class='me'>me :" + d.msg + "</p>");
                }else{
                    $("#chating").append("<p class='others'>" + d.userName + " :" + d.msg + "</p>");
                }

            }else{
                console.warn("unknown type!")
            }
            // $("#chating").append("<p>" + msg + "</p>");
        }
    }

    document.addEventListener("keypress", function(e){
        if(e.keyCode == 13){ //enter press
            send();
        }
    });
    //엔터 칠시 되게하는것
}

function send() {
    var option ={
        type: "message",
        sessionId : $("#sessionId").val(),
        roomNumber: userId,
        userName : userId,
        msg : $("#chatting").val()
    }
    ws.send(JSON.stringify(option))
    // ws.send(uN+" : "+msg);      //ws웹소켓 send
    $('#chatting').val("");     //텍스트창 리셋
}