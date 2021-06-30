<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="LayOut/header.jsp"></jsp:include>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/bookreturn.css">
<div class="container">
    <div class="row">
        <section class="content">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="pull-right">
                            <div class="btn-group">
                                <button type="button" class="btn btn-success btn-filter active" >전체</button>
                                <button type="button" class="btn btn-warning btn-filter">즐겨찾기</button>
                                <button type="button" class="btn btn-danger btn-filter">반납예정</button>
                            </div>
                        </div>
                            <table class="table table-filter">
                                <tbody>
                                <c:forEach var="book" items="${list}">
                                <tr data-status="pagado">
                                    <td>
                                        <div class="ckbox">
                                            <input type="checkbox" id="checkbox${book.idx}" value="${book.idx}">
                                            <label for="checkbox${book.idx}"></label>
                                        </div>
                                    </td>
                                    <td>
                                        <a href="javascript:;" class="star">
                                            <i class="glyphicon glyphicon-star"></i>
                                        </a>
                                    </td>
                                    <td>
                                        <div class="media">
                                            <div class="media-body">
                                                <span class="media-meta pull-right">
                                                    <fmt:formatDate value="${book.borrowedBookDto.return_date}" pattern="yyyy-MM-dd"/>
                                                </span>
                                                <h4 class="title">
                                                    ${book.name}
                                                    <font class="media-meta">${book.author}</font>
                                                    <span class="pull-right pagado">
                                                        <c:choose>
                                                            <c:when test="${book.borrow
                                                             && now.month+1==book.borrowedBookDto.return_date.month+1
                                                             && now.date+1==book.borrowedBookDto.return_date.date+1}">
                                                                <font color="red">반납예정</font>
                                                            </c:when>
                                                            <c:otherwise>
                                                                대출중
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </h4>
                                            </div>
                                        </div>
                                    </td>
                                </tr> <!-- tr.....end -->
                                </c:forEach>
                                </tbody>
                            </table>
                    </div>
                    <div class="panel-footer">
                        <div class="text-right">
                            <a id="book-return" class="btn btn-danger">반납하기</a>
                            <a href="#" class="btn btn-primary">연장하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </div>
</div>
<br>
<br>
<br>

<jsp:include page="LayOut/footer.jsp"></jsp:include>
