package com.library.application.exception.handler;

import com.library.application.exception.*;
import org.apache.commons.fileupload.FileUploadException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
// RestController 와 마찬가지로 ResponseBody 개념
// 자동으로 메모리에 해당 에러핸들링 클래스가 저장된다.
@RestControllerAdvice
public class BadResponseHandler {

    //핸들링이 저장되고 UserLoginError 처리를 할경우 핸들링이 에러처리+ JSON 으로 에러메세지를 응답한다.
    @ExceptionHandler({UserLoginErrorException.class,UserNotDeleteException.class, FileUploadException.class})
    public String UserLoginException(UserLoginErrorException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(BookNotFoundException.class)
    public String BookNotFoundException(BookNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    public String TokenErrorException(BookNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(BookExtendException.class)
    public String BookExtendException(BookExtendException ex ){
        return ex.getMessage();
    }

    @ExceptionHandler(BookDuplicationException.class)
    public String BookDuplicationException(BookDuplicationException ex){return ex.getMessage();}

}
