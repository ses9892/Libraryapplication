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
        })
        $('#passwordCheck').keyup(function (){
            _this.passwordCheck();
        })
    },
    register : function (){
        var vo ={
            userId: $('#userId').val(),
            pwd: $('#password').val(),
            name: $('#name').val(),
            phone: $('#phone').val(),
            email: $('#email').val(),
            gender: $('input:checkbox[name=gender]:checked').val()
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
            password: $('#pwd').val()
        }
        var new_data = "userId="+$('#userId').val()+"&password="+$('#pwd').val()
        console.log(data);
        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            // data: JSON.stringify(data)
            data : {
                userId: $('#userId').val(),
                password: $('#pwd').val()
            }
        }).done(function (token) {
            console.log(token)
            if($('input[name=remember]').prop("checked")){
                localStorage.setItem("remember",token.item.userId);
            }else {
                localStorage.clear()
            }
                localStorage.setItem("jwt",token.item.token);
                alert(token.item.meg);
                location.href=token.item.url
        }).error(function (error) {
        })
    },
    duplication : function (){
        var data = {
            userId: $('#userId').val()
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
                $('#register').attr("disabled",false)
            }
            if(!result){
                alert('중복된 아이디입니다. 다시확인해주세요.')
                $('#register').attr("disabled",true)
            }
        })
    },
    passwordCheck : function (){
        console.log('d')
        console.log(($('#passwordCheck').val().trim().length));
        if($('#password').val().trim()===$('#passwordCheck').val().trim()){
            $('#checkResult').html('<div style="color: #4cae4c">패스워드가 일치합니다</div>');
        }else{
            $('#checkResult').html('<div style="color: #b92c28">패스워드가 일치하지 않습니다.</div>')
        }
        if($('#passwordCheck').val().trim().length==0){
            $('#checkResult').html('<div ></div>');
        }

    }//passwordCheck...end
}  /**  main ...end*/

main.init();
// 이렇겧하는 이유는? 여러사람 공용으로 쓰는 js파일인 만큼 전역,멤버를 확실하게 다지기 위해서