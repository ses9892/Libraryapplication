var main = {
    init: function () {
        var _this = this;
        $('#select-topic').change(function () {
            _this.changeTopic();
        });
        $('#notborrow').click(function () {
            _this.notborrow();
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
    }
}  /**  main ...
main.init();
