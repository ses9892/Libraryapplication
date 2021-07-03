var main = {
    init: function () {
        var _this = this;
        $('#select-topic').change(function () {
            _this.changeTopic();
        });
        $('#notborrow').click(function () {
            _this.notborrow();
        });
        $('#booklend').click(function () {
            _this.booklend();
        });
        $('#book-return').click(function (){
            _this.bookReturn();
        })
        $('#book-extend').click(function (){
            _this.bookExtend();
        })
        $('a[name=favorites]').click(function (){
            _this.favorites(this);
        })

    },
    changeTopic : function (){
        var topic = $('#select-topic').select().val();
        if(topic=='All'){
            location.href='/library/booklend/all';
        }else{
            location.href='/library/booklend/'+topic;
        }
    },
    notborrow : function (){
        location.href='/library/booklend/not';
    },
    selectBook : function (){

    },
    booklend : function () {
        $.ajax({
            type: 'GET',
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            url: '/library/book/'+$('#book-idx').val()+'/lend',
            contentType: 'application/json; charset=utf-8'
        }).done(function (meg){
                alert(meg);
                location.reload(true);
        }).error(function (meg){
                alert(meg.responseJSON.message);
                location.reload(true);

        })
    },
    //반납하기 버튼 -->  체크박스 검사 --> 책반납
    bookReturn : function (){
        var idx = $('input[type=checkbox]:checked').val();

        $.ajax({
            type: "DELETE",
            url: "/library/book/return/"+idx,
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            contentType: 'application/json; charset=utf-8'

        }).done(function (data){
            alert(data)
        }).error(function (data){
            alert(data.responseJSON.message)
        }).complete(function (){
            location.reload();
        })

    },

    bookExtend : function (){
        var idx = $('input[type=checkbox]:checked').val();

        $.ajax({
            type: "PUT",
            url: "/library/book/return/"+idx,
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            contentType: 'application/json; charset=utf-8'
        }).done(function (data){
            alert(data)
        }).error(function (error){
            alert(error)
        }).complete(function (){
            location.reload();
        })
    },
    favorites : function (star){
        var idx = star.id;
        idx = idx.replace("favorites",""); // idx
        $.ajax({
            type: "PUT",
            url: "/library/book/favorites/"+idx,
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            contentType: 'application/json; charset=utf-8'
        }).done(function (data){
        }).error(function (error){
            alert(error)
        }).complete(function (){
            location.reload();
        })
    }

}  /**  main ...end*/
main.init();
