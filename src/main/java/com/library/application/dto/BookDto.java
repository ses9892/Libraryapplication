package com.library.application.dto;

import lombok.Data;

@Data
public class BookDto {
    private int idx;
    private String userId;
    private String name;
    private String author;
    private String publisher;
    private String topic;
    private String topic2;

    private Boolean borrow;
    private String save_name;




}
