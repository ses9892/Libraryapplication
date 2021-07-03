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
                                <button type="button" class="btn btn-success btn-filter active" id="allbtn" data-target="book-all">전체</button>
                                <button type="button" class="btn btn-warning btn-filter" data-target="book-favorites">즐겨찾기</button>
                            </div>
                        </div>
                            <table class="table table-filter">
                                <tbody>
                                <c:forEach var="book" items="${list}">
                                    <c:if test="${book.favorites}">
                                        <tr data-status="book-favorites">
                                    </c:if>
                                    <c:if test="${!book.favorites}">
                                        <tr data-status="book-all">
                                    </c:if>
                                        <td>
                                            <div class="ckbox">
                                                <input name="checkbox" type="checkbox" id="checkbox${book.idx}" value="${book.idx}">
                                                <label for="checkbox${book.idx}"></label>
                                            </div>
                                        </td>
                                        <td>
                                            <c:if test="${!book.favorites}">
                                                <a class="star" name="favorites" id="favorites${book.idx}">
                                            </c:if>
                                            <c:if test="${book.favorites}">
                                                <a class="star-checked" name="favorites" id="favorites${book.idx}">
                                            </c:if>
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
                            <a id="book-extend" class="btn btn-primary">연장하기</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </div>
</div>
<script>
$('input[name=checkbox]').click(function (){
    if($(this).prop('checked')){
        $('input[type="checkbox"][name="checkbox"]').prop('checked',false);
        $(this).prop('checked',true);
    }
})
$('.btn-filter').on('click', function () {
    var $target = $(this).data('target');
    console.log($target)
    if ($target != 'book-all') {
        $('.table tr').css('display', 'none');
        $('.table tr[data-status="' + $target + '"]').fadeIn('slow');
        if($('.table tr[data-status="' + $target + '"]')[0]==null){
            alert('즐겨찾기한 항목이 존재하지 않습니다.');
            $('#allbtn').click();
        }
    } else {
        $('.table tr').css('display', 'none').fadeIn('slow');
    }
});

</script>

<jsp:include page="LayOut/footer.jsp"></jsp:include>
