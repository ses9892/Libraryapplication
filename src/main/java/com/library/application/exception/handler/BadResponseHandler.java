package com.library.application.exception.handler;

import com.library.application.exception.UserLoginErrorException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BadResponseHandler {

    @ExceptionHandler(UserLoginErrorException.class)
    public String UserLoginException(UserLoginErrorException ex){
        return ex.getMessage();
    }
}
