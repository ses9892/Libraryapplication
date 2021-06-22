var main = {
    init: function () {
        var _this = this;
        $('#btn-save').click(function () {
            _this.bookSave();
        });
    },
    bookSave : function (){
        $.ajax({
            type: 'GET',
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            url: '/check/book',
            contentType: 'application/json; charset=utf-8',
        }).done(function (url){
            location.href=url;
        }).error(function (){
            alert('유효하지 않는 회원입니다.')
            location.href='/library'
        })
    }
}
main.init();
// 이렇겧하는 이유는? 여러사람 공용으로 쓰는 js파일인 만큼 전역,멤버를 확실하게 다지기 위해서