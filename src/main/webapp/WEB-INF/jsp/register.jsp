<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>회원가입</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/login.css"></link>
</head>
<body>
<div class="container" style="margin-top: 100px">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">도서관 회원가입</h3>
                </div>
                <div class="panel-body">
                    <form accept-charset="UTF-8" role="form">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="yourmail@example.com"  id="userId" name="userId" type="text">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" id="password" name="password" type="password" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password-Check" id="passwordCheck" name="passwordCheck" type="password" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="이름" name="name" id="name" type="text" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="010-XXXX-XXXX" name="phone" id="phone" type="text" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="XXXX@XXXXX.XXX" name="email" id="email" type="text" value="">
                            </div>
                            <div class="form-group">
                                남<input type="checkbox" name="gender" value="men"> 여 <input type="checkbox" name="gender" value="women">
                            </div>
                            <input class="btn btn-lg btn-success btn-block" id="register" type="button" value="회원가입">
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/index.js"></script>
</body>
</html>