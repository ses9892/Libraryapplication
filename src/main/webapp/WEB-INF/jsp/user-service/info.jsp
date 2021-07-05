<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../LayOut/header.jsp"></jsp:include>
<jsp:include page="../LayOut/mypage.jsp"></jsp:include>
</div><!-- /.container-fluid -->
<div class='span8 main'>
    <form action="/user-service/info/${user.userId}" method="post" onsubmit="return check()" >
        <div class="panel panel-default">
            <div class="panel-heading">Info</div>
            <div class="panel-body">
                <div class="form-group">
                    <div class="col-md-12"><strong>성명</strong></div>
                    <div class="col-md-12">
                        <input type="text" class="form-control" name="name" value="${user.name}" readonly />
                        <input type="hidden" name="idx" value="${user.idx}"/>
                    </div>
                </div>
                    <div class="form-group">
                        <div class="col-md-12"><strong>ID</strong></div>
                        <div class="col-md-12">
                            <input type="text" name="userId" class="form-control" value="${user.userId}" readonly />
                        </div>
                    </div>
                <div class="form-group">
                    <div class="col-md-6 col-xs-12">
                        <strong>비밀번호</strong>
                        <input type="password" name="pwd" class="form-control" value="" />
                    </div>
                    <div class="span1"></div>
                    <div class="col-md-6 col-xs-12">
                        <strong>비밀번호 확인</strong>
                        <input type="password" name="pwdCheck" class="form-control" value="" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12"><strong>Email</strong></div>
                    <div class="col-md-12">
                        <input type="text" name="email" class="form-control" value="${user.email}" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12"><strong>P.H</strong></div>
                    <div class="col-md-12">
                        <input type="text" name="phone" class="form-control" value="${user.phone}" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-3"><strong>대출 도서</strong></div>
                    <div class="col-md-3"><strong>성별</strong></div>
                    <div class="col-md-6"><strong>가입 날짜</strong></div>
                    <div class="col-md-3"><input type="text" name="borrowed_book" class="form-control" value="${user.borrowed_book} 권"
                    readonly/></div>
                    <div class="col-md-3">
                        <select name="gender" class="form-control">
                            <option value="men"
                                    <c:if test="${user.gender eq 'men'}">selected</c:if>
                            >남</option>
                            <option value="women"
                                    <c:if test="${user.gender eq 'women'}">selected</c:if>
                            >여</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <input type="text" class="form-control" value="<fmt:formatDate value="${user.createdAt}" pattern="YYYY-MM-dd"/>" readonly>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-12">&nbsp;</div>
                    <div class="col-md-12 text-center">
                        <input type="submit" class="btn btn-default" value="수정하기">
                    </div>
                </div>
            </div>
    </form>
</div>
<script>
    function check() {
        var pwd = $('input[name=pwd]').val();
        var pwdCheck = $('input[name=pwdCheck]').val();
        if(pwd.trim().length==0 && pwdCheck.trim().length==0){
            alert('비밀번호를 작성해주세요');
            $('input[name=pwd]').val("").focus();
            return false;
        }
        if(pwd!=pwdCheck){
            alert('비밀번호가 서로 일치하지 않습니다.');
            $('input[name=pwd]').val("").focus();
            $('input[name=pwdCheck]').val("");
            return false;
        }
        return true;
    }
</script>
<jsp:include page="../LayOut/footer.jsp"></jsp:include>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/css/mypage.css">
