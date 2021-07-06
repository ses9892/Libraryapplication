<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../LayOut/header.jsp"></jsp:include>
<jsp:include page="../LayOut/mypage.jsp"></jsp:include>
<input type="hidden" name="flag" value="${flag}">
</div><!-- /.container-fluid -->
<div class='span8 main'>
    <input type="hidden" name="flag" value="infoChange">
    <form action="/user-service/info/${user.userId}" method="post">
        <div class="col-lg-3 col-sm-2"></div>
        <div class="col-lg-5 col-sm-6">
            <div class="well login-box">
                    <legend style="text-align: center">자동반납 설정</legend>
                    <div class="form-group text-center" style="background-color: white; border: 1px solid #000000">
                        <label>자동반납</label><br><br>
                        <label for="auto">자동</label>
                        <input type="radio" id="auto" name="autoReturn" value="true"
                            <c:if test="${user.autoReturn}">checked="checked"</c:if>
                        >
                        <label for="passive">수동</label>
                        <input type="radio" id="passive" name="autoReturn" value="false"
                             <c:if test="${!user.autoReturn}">checked="checked"</c:if>
                        >
                    </div>
                    <div class="form-group text-center">
                        <button class="btn btn-danger btn-cancel-action" onclick="history.back();">돌아가기</button>
                        <input type="submit" class="btn btn-success btn-login-submit" value="수정"/>
                    </div>
            </div>
        </div>
    </form>
<jsp:include page="../LayOut/footer.jsp"></jsp:include>
</div>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
