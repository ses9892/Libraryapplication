var main = {
    init: function () {
        var _this = this;
        $('#user-pwdCheck').click(function () {
            _this.pwdCheck();
        });
        $('a[name=myPage]').click(function (){
            _this.myPage();
        })
    },
    pwdCheck : function (){
        var pwd= $('#user-password').val();
        $.ajax({
            type: "GET",
            url: "/user-service/pwdCheck",
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            data: {
                pwd: pwd,
                flag: $( 'input[name="flag"]' ).val()
            },
            contentType: 'application/json; charset=utf-8'
        }).success(function (data){
            if(data=='비밀번호가 일치하지 않습니다.'){
                alert(data)
                $('#user-password').val("").focus();
            }else{
                if($('input[name="flag"]' ).val()=='userDelete'){
                    var result = confirm("삭제후에는 책은 자동반납되며 이후 데이터를 복원할 수 없습니다.");
                    if(result){
                        $.ajax({
                            type: "DELETE",
                            url: data,
                            contentType: 'application/json; charset=utf-8'
                        }).done(function (result){
                            alert(result);
                        }).error(function (result){
                            console.log(result)
                        })
                        return true;
                    }
                }
                if($('input[name="flag"]' ).val()=='infoChange' || $('input[name="flag"]' ).val()=='autoReturn' ){
                 location.href= data+"?key=bearer"+localStorage.getItem("jwt");
                }
            }
        }).complete(function (){
            $('#user-password').val("").focus();
        })
    },
    myPage : function (){
        if(localStorage.getItem("jwt")==null){
            alert('정상적인 로그인후 마이페이지를 이용해주세요!');
            $('#logOut').click();
            return null;
        }
        location.href = '/user-service/mypage'
    }


}  /**  main ...end*/
main.init();
