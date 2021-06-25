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
        }).error(function (meg){
                alert('실패')
        })
    }
}  /**  main ...end*/
main.init();
