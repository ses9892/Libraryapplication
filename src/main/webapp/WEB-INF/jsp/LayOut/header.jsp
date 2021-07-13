<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="//code.jquery.com/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.css"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="/js/bootstrap.js"></script>
<link rel="stylesheet" href="/css/index.css">
<link rel="stylesheet" href="/css/mainPage.css">
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
                    <li><a href="/library"><spring:message code="library.home"/></a></li>
                    <li><a name="btn-save"><spring:message code="library.bookRegister"/></a></li>
                    <li><a href="/library/list/all"><spring:message code="library.bookLending"/></a></li>
                    <li>
                        <a class="collapsed" id="mutifulLang"  data-toggle="collapse" href="#nav-collapse3" aria-expanded="false" aria-controls="nav-collapse3">
                            <spring:message code="library.convert"/>
                        </a>
                    </li>
                    <li><a name="myPage"><spring:message code="library.mypage"/></a></li>
                    <li>
                        <a id="logOut" class="btn btn-default btn-outline btn-circle" onclick="logout()">
                            LogOut
                        </a>
                    </li>
                </ul>
                </div>
                <div class="collapse nav navbar-nav nav-collapse slide-down justify-content-center" id="nav-collapse3" style="z-index: 2">
                    <form class="navbar-form" role="search">
                        <ul class="centered text-center">
                        <div class="form-group">
                            <a href="/library?lang=kr" class="btn btn-default btn-circle active">한국어</a>
                            <a href="library?lang=ja" class="btn btn-default btn-circle">日本</a>
                            <a href="library?lang=en" class="btn btn-default btn-circle">English</a>
                        </div>
                        <div class="form-group" style="float: right">
                            <a class="btn btn-danger collapsed"  data-toggle="collapse" href="#nav-collapse3" aria-expanded="false" aria-controls="nav-collapse3">닫기</a>
                        </div>
                        </ul>
                    </form>
                </div>
            </div><!-- /.navbar-collapse -->

    </nav>
        </div><!-- /.container -->

