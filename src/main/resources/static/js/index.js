var main = {
    init: function () {
        var _this = this;
        $('#btn-save').click(function () {
            _this.save();
        });
        $('#login-btn').click(function () {
            _this.login();
        });
        $('.post-select').click(function () {
            _this.select(this);
        })
        $('#logout').click(function () {
            _this.logOut();
        })
        $('#register').click(function () {
            _this.register();
        })
    },
    register : function (){
        var vo ={
            userId: $('#userId').val(),
            password: $('#password').val(),
            name: $('#name').val(),
            phone: $('#phone').val(),
            email: $('#email').val(),
            gender: $('input:checkbox[name=gender]:checked').val()
        }
        //console.log(vo);
        $.ajax({
            type: 'POST',
            url: '/user/register',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(vo)
        }).done(function (meg){
            alert(meg.meg);
            location.href='/'
        }).error(function (){
            alert('회원가입에 실패 하였습니다.')
            location.href='/register'
        })
    }
};
main.init();
// 이렇겧하는 이유는? 여러사람 공용으로 쓰는 js파일인 만큼 전역,멤버를 확실하게 다지기 위해서