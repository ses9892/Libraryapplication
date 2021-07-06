package com.library.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotDeleteException extends RuntimeException{
    public UserNotDeleteException(String message) {
        super(message);
    }
}
