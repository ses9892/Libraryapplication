var main = {
    init: function () {
        var _this = this;
        $('#btn-save').click(function () {
            _this.save();
        });
        $('#login').click(function () {
            _this.login();
        });
        $('#logout').click(function () {
            _this.logOut();
        })
        $('#register').click(function () {
            _this.register();
        })
        $('#duplicate-check').click(function (){
            _this.duplication();
            _this.btnEnable();
        })
        $('#passwordCheck').keyup(function (){
            _this.passwordCheck();
            _this.btnEnable();
        })
        $('#email-send').click(function (){
            _this.emailSend();
        })
        $('#email-code-btn').click(function (){
            _this.emailCodeCheck();
            _this.btnEnable();
        })
        $('.nP').keyup(function (){
            _this.btnEnable();
        })
        $('#idSerach').click(function (){
            _this.PasswordReSet();
        })
    },
    register : function (){
        var vo ={
            userId: $('#userId2').val(),
            pwd: $('#password2').val(),
            name: $('#name').val(),
            phone: $('#phone').val(),
            email: $('#email').val(),
        }
        console.log(vo);
        $.ajax({
            type: 'POST',
            url: '/register',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(vo)
        }).done(function (meg){
            alert(meg.meg);
            location.href='/'
        }).error(function (){
            alert('회원가입에 실패 하였습니다.')
            // location.href='/register'
        })
    },
    login : function () {
        var data = {
            userId: $('#userId').val(),
            password: $('#pwd').val(),
            rememberMe : $('#remember-id').prop('checked')
        }
        console.log(data);
        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            data : {
                userId: $('#userId').val(),
                password: $('#pwd').val(),
                rememberMe : $('#remember-id').prop('checked')
            }
        }).done(function (token) {
            console.log(token)
            if($('input[name=remember]').prop("checked")){
                localStorage.setItem("remember",token.item.userId);
            }else {
                localStorage.clear()
            }
            if(token.item.type=='ok'){
                localStorage.setItem("jwt",token.item.token);   //토큰저장
                alert(token.item.meg);                          //환영메세지
                if($('#remember-id').prop('checked')){
                    localStorage.setItem("rememberId",true);
                }
                window.open("/chat/","Library Chating","top=50,width=850,height=620,resizable=no,menubar=no,directories=no,toolbar=no,location=no");
                location.href=token.item.url
            }else if(token.item.type=='failed'){
                alert(token.item.meg);
                location.reload();
            }
        }).error(function (error) {
            console.log(error)
        })
    },
    duplication : function (){
        var data = {
            userId: $('#userId2').val()
        }
        $.ajax({
            type: "POST",
            url: "/duplication",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (result){
            if(result){
                alert('사용가능한 아이디 입니다.')
                idcheck=true;
                // $('#register').attr("disabled",false)
            }
            if(!result){
                alert('중복된 아이디입니다. 다시확인해주세요.')
                idcheck=false
                // $('#register').attr("disabled",true)
            }
        })
    },
    passwordCheck : function (){
        console.log('d')
        console.log(($('#passwordCheck').val().trim().length));
        if($('#password2').val().trim()===$('#passwordCheck').val().trim()){
            $('#checkResult').html('<div style="color:black;font-weight: bold;font:inherit" >패스워드가 일치합니다</div>');
            pwdCheck=true;
        }else{
            $('#checkResult').html('<div style="color: #ff0000 ;font-weight: bold;font:inherit">패스워드가 일치하지 않습니다.</div>')
            pwdCheck=false;
        }
        if($('#passwordCheck').val().trim().length==0){
            $('#checkResult').html('<div ></div>');
        }

    //passwordCheck...end
    },
    emailSend : function (){
        // email-code , email-code-btn
        var email = $('#email').val().split('@');
        var data = {id : email[0],mail : email[1]}
        $.ajax({
            type: "POST",
            url: "/email",
            dataType: 'text',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (authKey){
            alert('메일이 발송 되었습니다.');
            if($.cookie('authKey') !=null){
                $.removeCookie('authKey');
            }
            $.cookie('authKey',authKey);
            $('#email-code').prop("type","text");
            $('#email-code-btn').prop("type","button");
            $('#email').prop("readOnly","readOnly");
        }).error(function (error){
            console.log(error);

        })
    },
    emailCodeCheck : function (){
        var authKey = $.cookie('authKey');
        var inputKey = $('#email-code').val().trim();
        if(inputKey.length==0){
            alert('인증번호를 입력해주세요');
            return false;}
        if(inputKey!=authKey) {
            alert('인증번호가 올바르지 않습니다.');
            return false;}
        alert('인증완료 되었습니다.');
        emailCheck=true;
        $('#email-code').prop("type","hidden");
        $('#email-code-btn').prop("type","hidden");
        $('#email').prop("readOnly","readOnly");
        $('#email-send').prop("disabled","disabled");

    },
    btnEnable : function (){
        var name = $('#name').val().trim()
        var ph = $('#phone').val().trim()
        if(name.length==0 || ph.length==0){
            return false;
        }
        if(idcheck && pwdCheck && emailCheck){
            $('#register').attr("disabled",false);
            $('#register').attr("style","background:red;")
        }
    },
    PasswordReSet: function (){
        if($('#forgetId').val().trim().length==0){
            alert('ID를 입력하고 다시 이용해주세요!');
            location.reload();
            return false;
        }
        var data =
            {userId : $('#forgetId').val()}
        $.ajax({
            type: "PUT",
            url: "/forgetPwd",
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (succees){
            $('#SearchingId').text(succees.email+"로 임시 비밀번호가 전송되었습니다.");
        }).error(function (error){
            $('#SearchingId').text(error.responseText);

        })
    }
}  /**  main ...end*/

main.init();
// 이렇겧하는 이유는? 여러사람 공용으로 쓰는 js파일인 만큼 전역,멤버를 확실하게 다지기 위해서