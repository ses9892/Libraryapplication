package com.library.application.ResponseVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseData {
    private String code;
    private String status;
    private String message;
    private Object item;

}
