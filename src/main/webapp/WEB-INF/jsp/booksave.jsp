<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="LayOut/header.jsp"></jsp:include>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="/css/booksave.css">
<div class="container">
        <form class="form-horizontal" action="/library/book" method="post" enctype="multipart/form-data"onsubmit="return bookSaveCheck()">
        <div class="card-content">
                <!--/* update의 경우 서버로 전달할 게시글 번호 (PK) */-->
                <input type="hidden" name="userId" value="${userId}">
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
                    <label for="publisher" class="col-sm-2 control-label">분야</label>
                    <div class="col-sm-10">
                        <select name="topic" id="topic" class="form-control">
                            <option value="IT">IT</option>
                            <option value="health">건강</option>
                            <option value="social">사회</option>
                            <option value="religion">종교</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="publisher" class="col-sm-2 control-label">분야2</label>
                    <div class="col-sm-10">
                        <select name="topic2" id="topic2" class="form-control">
                            <option value="" selected="selected">선택 안함</option>
                            <option value="IT">IT</option>
                            <option value="health">건강</option>
                            <option value="social">사회</option>
                            <option value="religion">종교</option>
                        </select>
                    </div>
                </div>
            <div class="form-group filebox bs3-primary">
                <label for="file_0" class="col-sm-2 control-label">PDF</label>
                <div class="col-sm-10">
                    <input type="text" class="upload-name" value="파일 찾기" readonly />
                    <label for="pdf" class="control-label">찾아보기</label>
                    <input type="file" name="files" id="pdf" class="upload-hidden" onchange="changeFilename(this)" accept=".pdf" />
                    <button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
                        <i class="fa fa-minus" aria-hidden="true"></i>
                    </button>
                    <hr>
                </div>
            </div>
            <div data-name="fileDiv" class="form-group filebox bs3-primary">
                <label for="pdf" class="col-sm-2 control-label">이미지</label>
                <div class="col-sm-10">
                    <input type="text" class="upload-name" value="파일 찾기" readonly />
                    <label for="file_0" class="control-label">찾아보기</label>
                    <input type="file" name="files" id="file_0" class="upload-hidden" onchange="changeFilename(this)" />
                    <button type="button" onclick="addFile()" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
                        <i class="fa fa-plus" aria-hidden="true"></i>
                    </button>
                    <button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
                        <i class="fa fa-minus" aria-hidden="true"></i>
                    </button>
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
    let fileIdx = 0; /*[- 파일 인덱스 처리용 전역 변수 -]*/
    let fileCnt = 1;
    function addFile(){
        fileCnt++;
        if (fileCnt > 3) {
            alert('파일은 최대 세 개까지 업로드 할 수 있습니다.');
            return false;
        }
        fileIdx++;
        var fileHtml = '<div data-name="fileDIv" class="form-group filebox bs3-primary">'+
                        '<label for="file_'+fileIdx+'" class="col-sm-2 control-label"></label>'+
                        '<div class="col-sm-10">'+
                        '<input type="text" class="upload-name" value="파일 찾기" readonly />'+
                        '<label for="file_'+fileIdx+'" class="control-label">찾아보기</label>'+
                        '<input type="file" name="files" id="file_'+fileIdx+'" class="upload-hidden" onchange="changeFilename(this)" />'
        +'<button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">'+
            '<i class="fa fa-minus" aria-hidden="true"></i>'+
            '</button></div></div>';
        $('#btnDiv').before(fileHtml);
    }
    function removeFile(elem) {

        const prevTag = $(elem).prev().prop('tagName');
        console.log(prevTag);
        // console.log(prevTag)
        if (prevTag === 'BUTTON' || prevTag==='INPUT') {
            const file = $(elem).prevAll('input[type="file"]');
            const filename = $(elem).prevAll('input[type="text"]');
            // console.log(file.val())
            file.val(''); //진짜 값을바꾸고
            filename.val('파일 찾기'); //바뀐 텍스트 값도 바꾸고
            return false;
        }


        const target = $(elem).parent().parent();
        console.log(target);
        target.remove();
        fileCnt--;
        console.log(fileCnt)
    }
    function changeFilename(file) {

        file = $(file);
        const filename = file[0].files[0].name;
        const target = file.prevAll('input');
        target.val(filename);
    }
    function bookSaveCheck(){
        var name = $('#name').val().trim();
        var author = $('#writer').val().trim();
        var publisher = $('#publisher').val().trim();
        var topic = $('#topic').val().trim();
        var topic2 = $('#topic2').val().trim();
        if(name.length==0 || author.length==0 || publisher.length==0){
            alert('필요한 정보를 모두 입력해주세요!');
            return false;
        }
        if(topic == topic2){
            alert('중복된 분야가 선택 되었습니다.')
            return false;
        }
        return true;

    }
</script>
<jsp:include page="LayOut/footer.jsp"></jsp:include>
