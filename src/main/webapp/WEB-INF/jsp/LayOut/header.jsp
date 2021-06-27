<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<%--<link rel="stylesheet" href="/css/bootstrap.min.css">--%>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="/js/bootstrap.js"></script>
<link rel="stylesheet" href="/css/index.css">
<head>
    <title>Library Home</title>
</head>
<body>
<div class="container-fluid">

    <!-- Second navbar for sign in -->
    <nav class="navbar navbar-default">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#myNavbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Library-Service</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="myNavbar">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="/library">Home</a></li>
                    <li><a id="btn-save">도서 등록</a></li>
                    <li><a href="/library/booklend/all">도서 대출</a></li>
                    <li><a id="btn-return">도서 반납</a></li>
                    <li>
                        <a class="collapsed"  data-toggle="collapse" href="#nav-collapse3" aria-expanded="false" aria-controls="nav-collapse3">Search</a>
                    </li>
                    <li><a href="#">마이페이지</a></li>
                    <li>
                        <a class="btn btn-default btn-outline btn-circle" onclick="logout()">
                            LogOut
                        </a>
                    </li>
                </ul>
                </div>
                <div class="collapse nav navbar-nav nav-collapse slide-down" id="nav-collapse3" style="z-index: 2">
                    <form class="navbar-form navbar-right" role="search">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search" />
                        </div>
                        <button type="submit" class="btn btn-danger">Go</button>
                        <a class="btn btn-danger collapsed"  data-toggle="collapse" href="#nav-collapse3" aria-expanded="false" aria-controls="nav-collapse3">닫기</a>
                    </form>
                </div>
            </div><!-- /.navbar-collapse -->

        </div><!-- /.container -->
    </nav>
