package com.library.application.ResponseVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseToken {
    private String accessToken;
    private String tokenType;
}
