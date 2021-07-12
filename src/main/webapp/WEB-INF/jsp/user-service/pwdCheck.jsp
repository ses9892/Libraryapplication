<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../LayOut/header.jsp"></jsp:include>
<jsp:include page="../LayOut/mypage.jsp"></jsp:include>
<input type="hidden" name="flag" value="${flag}">
</div><!-- /.container-fluid -->
<div class='span8 main'>
    <div class="col-lg-3 col-sm-2"></div>
    <div class="col-lg-5 col-sm-6">
        <div class="well login-box">
                <legend style="text-align: center">비밀번호 확인</legend>
                <div class="form-group">
                    <label for="user-password">Password</label>
                    <input id="user-password" value='' placeholder="Password" type="password" class="form-control" required/>
                </div>
                <div class="form-group text-center">
                    <button class="btn btn-danger btn-cancel-action" onclick="history.back();">돌아가기</button>
                    <input type="button" class="btn btn-success btn-login-submit" id="user-pwdCheck" value="확인"/>
                </div>
        </div>
    </div>
<jsp:include page="../LayOut/footer.jsp"></jsp:include>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
