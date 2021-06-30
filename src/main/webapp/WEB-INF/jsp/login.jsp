<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>스프링부트 웹서비스</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <link href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/css/login.css"></link>
</head>
<body>
<div align = "center">
	<p>
		${clientLocale }
		<a href = "./?lang=ko">한국어</a>
		<a href = "./?lang=en">English</a>
	</p>
</div>
<div class="container" style="margin-top: 100px">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><spring:message code="login.title" text="default text"/></h3>
                </div>
                <div class="panel-body">
                    <form accept-charset="UTF-8" role="form">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="yourmail@example.com" id="userId" name="email" type="text">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" id="pwd" type="password" value="">
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input name="remember" type="checkbox" value="Remember Me">
                                    <spring:message code = "login.remember" text="default text"/>
                                </label>
                                <a style="float: right" href="/register"><spring:message code = "login.join" text="default text"/></a>
                            </div>
                            <input class="btn btn-lg btn-success btn-block" id="login" type="button" 
                            value= '<spring:message code = "login.login" text="default text"/>'>
                        </fieldset>
                    </form>
                    <hr/>
                    <input class="btn btn-lg btn-facebook btn-block" type="submit" 
                    	value="<spring:message code = 'login.google' text="default text"/>">
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/js/index.js"></script>
</body>
</html>