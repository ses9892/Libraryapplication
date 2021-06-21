
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<!--index.js 추가-->
<script src="/js/index.js"></script>
<script>
    if(localStorage.getItem('jwt')!=null){
        $('#login').text('LogOut').attr('href','/logout').attr('id','logout').attr('href','#').attr('onclick','logout()');
    }
    function logout() {
        alert("로그아웃 완료!")
        localStorage.removeItem('jwt');
        location.reload();
    }
</script>
</body>
</html>