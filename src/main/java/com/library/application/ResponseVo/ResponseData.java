package com.library.application.ResponseVo;

import lombok.Data;

@Data
public class ResponseData {
    private String code;
    private String status;
    private String message;
    private Object item;
}
