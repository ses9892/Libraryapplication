<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-xs-12 col-md-offset-1 col-md-10 text-center">
            <p>I created this because I loved the design done by <a href="http://bootsnipp.com/maridlcrmn" target="_blank">maridlcrmn</a> on her <a href="http://bootsnipp.com/snippets/ZkpRl" target="_blank">Toggle navbar</a> but wanted a fancier slide down naviation when you click on the toggle button. However my code does come with a limitation, you will not be able to use dropdowns within your navigation if using this method because <code>overflow</code> is set to <strong>hidden</strong>. You can see this in action by trying to use the very last navigation on this snippet.</p>
        </div>
    </div>
</div>

<!--index.js 추가-->
<script src="/js/Token.js"></script>
<script src="/js/bookAction.js"></script>
<script src="/js/userAction.js"></script>
<script>
    function logout() {
        localStorage.removeItem('jwt');
        localStorage.removeItem('Type');
        location.reload();
        location.href = "/logout";
    }
</script>

</body>
</html>