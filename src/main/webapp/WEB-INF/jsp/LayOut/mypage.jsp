<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-default sidebar" role="navigation" style="margin-left: 15px;">
    <div class="container-fluid" style="">
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">MyPage<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">내정보<span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a>
                    <ul class="dropdown-menu forAnimate" role="menu">
                        <li><a href="/user-service/info?flag=infoChange">정보수정</a></li>
<%--                        <li><a href="#">등록한 도서</a></li>--%>
                        <li><a href="/user-service/info?flag=userDelete">회원 탈퇴</a></li>
                        <li class="divider"></li>
                        <li><a href="/user-service/info?flag=autoReturn">자동반납 설정</a></li>
                    </ul>
                </li>
                <li ><a id="returnPage">빌린 도서<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
<%--                <li ><a href="#">도서 조회<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>--%>
            </ul>
        </div>
    </div>
</nav>
</div><!-- /.container-fluid -->

