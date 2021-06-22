<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="LayOut/header.jsp"></jsp:include>
<link rel="stylesheet" href="/css/booksave.css">
<div class="container">
        <form class="form-horizontal" action="/library/book" method="post" enctype="multipart/form-data">
        <div class="card-content">
                <!--/* update의 경우 서버로 전달할 게시글 번호 (PK) */-->

                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">제목</label>
                    <div class="col-sm-10">
                        <input type="text" id="name" name="name" class="form-control" placeholder="제목을 입력해 주세요." />
                    </div>
                </div>

                <div class="form-group">
                    <label for="writer" class="col-sm-2 control-label">저자</label>
                    <div class="col-sm-10">
                        <input type="text" id="writer" name="author" class="form-control" placeholder="이름을 입력해 주세요." />
                    </div>
                </div>

                <div class="form-group">
                    <label for="publisher" class="col-sm-2 control-label">출판사</label>
                    <div class="col-sm-10">
                        <input type="text" id="publisher" name="publisher" class="form-control" placeholder="출판사 입력해 주세요."></input>
                    </div>
                </div>
            <div class="form-group">
                <label for="publisher_date" class="col-sm-2 control-label">발행년도</label>
                <div class="col-sm-10">
                    <input type="text" id="publisher_date" name="publisher_date" class="form-control" placeholder="출판사 입력해 주세요."></input>
                </div>
            </div>
            <div data-name="fileDiv" class="form-group filebox bs3-primary">
                <label for="file_0" class="col-sm-2 control-label">파일1</label>
                <div class="col-sm-10">
                    <input type="text" class="upload-name" value="파일 찾기" readonly />
                    <label for="file_0" class="control-label">찾아보기</label>
                    <input type="file" name="files" id="file_0" class="upload-hidden" onchange="changeFilename(this)" />
                </div>
            </div>
            <div id="btnDiv" class="btn_wrap text-center" th:object="${params}">
                <a onclick="history.back()" class="btn btn-default waves-effect waves-light">뒤로가기</a>
                <button type="submit" class="btn btn-primary waves-effect waves-light">저장하기</button>
            </div>
        </div>
    </form>
</div>
</div><!-- /.container-fluid -->
<script>
    function changeFilename(file) {

        file = $(file);
        const filename = file[0].files[0].name;
        const target = file.prevAll('input');
        target.val(filename);
    }
</script>
<jsp:include page="LayOut/footer.jsp"></jsp:include>
