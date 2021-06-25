<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="LayOut/header.jsp"></jsp:include>
<style>
    .bookline{
        border-top: 1px solid silver;
    }
</style>
${data}
<div class="container">
    <h1 class="well text-center">도서 상세</h1>
    <div class="col-lg-12 well">
        <div class="row">
            <form>
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-12 form-group">
                            <input type="hidden" id="book-idx" value="${data.idx}">
                            <label>도서명</label>
                            <div><p style="font-size: 3em; overflow:fragments ">${data.name}</p></div>
                        <hr size="2" class="bookline">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-1 form-group">
                        </div>
                        <div class="form-group col-sm-10">
                            <img src="/img/${data.save_name}" alt="..." class="img-rounded" style="width: 100%;max-height: 450px">
                        </div>
                        <div class="col-sm-1 form-group">
                        </div>
                    </div>
                      <hr size="2" class="bookline">
                    <div class="row">
                        <div class="col-sm-4 form-group">
                            <label>저자</label>
                            <p class="form-control">${data.author}</p>
                        </div>
                        <div class="col-sm-4 form-group">
                            <label>출판사</label>
                            <p class="form-control">${data.publisher}</p>
                        </div>
                        <div class="col-sm-4 form-group">
                            <label>주제/주제2</label>
                            <select class="form-control">
                                <option value="${data.topic}">${data.topic}</option>
                                <option value="${data.topic2}">${data.topic2}</option>
                            </select>
                        </div>
                        <hr size="2" color="pink">
                    </div>

                    <div class="row">
                        <div class="col-sm-11 form-group">
                        </div>
                        <div class="col-sm-1 form-group">
                            <div>
                            <label style="float: right">대출여부</label>
                            <c:if test="${data.borrow}">
                                <p style="color: #b92c28; float: right">불가능</p>
                            </c:if>
                            <c:if test="${!data.borrow}">
                                <p style="color: green;float: right">가능</p>
                            </c:if>
                            </div>
                        </div>
                    </div>
                        <hr size="2" class="bookline" style="margin-top: 0px">
                    <div class="form-group">
                        <label>책 소개</label>
                        <textarea class="form-control" style="resize: none; min-height: 300px;"></textarea>
                    </div>
                    <div class="col text-center">
                        <c:choose>
                            <c:when test="${data.borrow}">
                                <button type="button" class="btn btn-lg btn-info disabled">대출하기</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" id="booklend" class="btn btn-lg btn-info">대출하기</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>



</div><!-- /.container-fluid -->
<jsp:include page="LayOut/footer.jsp"></jsp:include>
