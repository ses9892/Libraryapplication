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
        $('#book-select').click(function (){
            _this.bookSelect();
        })

    },
    changeTopic : function (){
        var topic = $('#select-topic').select().val();
        if(topic=='All'){
            location.href='/library/list/all';
        }else{
            location.href='/library/list/'+topic;
        }
    },
    notborrow : function (){
        location.href='/library/list/not';
    },

    booklend : function () {
        $.ajax({
            type: 'POST',
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            // url: '/library/book/'+$('#book-idx').val()+'/lend',
            url: '/library/book/'+$('#book-idx').val(),
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
        if(idx==null){
            alert('반납할 항목을 체크해주세요')
            return null;
        }

        $.ajax({
            type: "DELETE",
            url: "/library/book/"+idx,
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
        if(idx==null){
            alert('연장할 항목을 체크해주세요')
            return null;
        }
        $.ajax({
            type: "PUT",
            // url: "/library/book/return/"+idx,
            url: "/library/book/"+idx,
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
    },
    bookSelect : function (){
        var idx = $('input[type=checkbox]:checked').val();
        if(idx==null){
            alert('조회할 도서를 체크해주세요')
            return null;
        }
        $.ajax({
            type: "GET",
            url: "/library/book/"+idx,
            headers:{
                'content-type':'application/json',
                'Authorization':'bearer'+localStorage.getItem('jwt')
            },
            contentType: 'application/json; charset=utf-8'
        }).done(function (data){
            window.open(data)
        }).error(function (emeg){
            alert(emeg.responseText);
            location.reload();
        })
    }

}  /**  main ...end*/
main.init();
