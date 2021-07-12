package com.library.application.ResponseVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSaveRequest {
    private String userId;
    private String name;
    private String author;
    private String publisher;
    private String topic;
    private String topic2;



}
