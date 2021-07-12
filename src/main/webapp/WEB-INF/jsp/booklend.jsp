<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="LayOut/header.jsp"></jsp:include>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/booksave.css">
<link rel="stylesheet" href="/css/booklend.css">

<div class="container">
        <div class="form-horizontal">
            <div class="card-content">
                    <div class="form-group">
                        <div class="col-sm-12">
                            <p>
                                <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
                                    대출가능 도서
                                </a>
                                <button class="btn btn-danger" id="notborrow" type="button" >
                                    대출불가능 도서
                                </button>
                                <a class="btn btn-danger" href="/pdf/viewer.html?file=/preview">
                                    테스트
                                </a>
                            </p>
                            <div class="collapse" id="collapseExample">
                                <div class="card card-body">
                                    <div class="col-xs-8 col-sm-8">
                                        <form>
                                            <div class="form-group">
                                                <label class="items">분야</label>
                                                <select name="topic" id="select-topic" class="form-control items" style="float: left">
                                                    <option value="all" selected>목록을 선택해주세요</option>
                                                    <option value="IT">IT</option>
                                                    <option value="health">건강</option>
                                                    <option value="social">사회</option>
                                                    <option value="religion">종교</option>
                                                </select>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </div>
                    </div>
            </div>
        </div> <!--container-->
<div class="container" style="padding-bottom: 20px">
    <hr style="margin-top: 0"/>
    <div class="row">
        <c:forEach var="book" items="${bookList}">
            <c:set var="bookSelect" value="/library/book?idx=${book.idx}"></c:set>
            <div class="col-md-4 col-sm-6">
                <div class="product-grid">
                    <div class="product-image">
                        <a href="${bookSelect}">
                            <img class="pic-1 pic" src="<c:url value="/img/${book.save_name}"/>" alt="${book.save_name}">
                        </a>
                        <ul class="social">
                            <li><a href="${bookSelect}" data-tip="도서 보기"><i class="fa fa-search"></i></a></li>
                            <li><a onclick="" data-tip="찜하기" id="cart"><i class="fa fa-shopping-cart"></i></a></li>
                        </ul>
                        <c:if test="${book.borrow}">
                        <span class="product-new-label" style="padding: 4px 10px; font-size: 20px; background-color: hotpink; font-weight: bold;">대출불가</span>
                        </c:if>
                        <c:if test="${!book.borrow}">
                            <span class="product-new-label" style="padding: 4px 10px; font-size: 20px; background-color: #67b168; font-weight: bold;">대출가능</span>
                        </c:if>
                    </div>

                    <ul class="rating">
                        <li>
                            <span class="glyphicon glyphicon-heart">&nbsp;</span>
                        </li>
                    </ul>


                    <div class="product-content">
                        <h3 class="title"><a href="${bookSelect}">${book.name}</a></h3>
                        <div class="price">${book.author}
                            <p>${book.topic},${book.topic2}</p>
                        </div>
                        <a class="add-to-cart" onclick="">찜하기</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    $(function (){
        var data=window.location.pathname;
        const words = data.split('/');
        $('#select-topic').select().val(words[3]);

    })
</script>
<jsp:include page="LayOut/footer.jsp"></jsp:include>
