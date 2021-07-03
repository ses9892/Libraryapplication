package com.library.application.dto;

import lombok.Data;

@Data
public class BookDto {
    private int idx;                //책번호
    private String userId;          //등록userId
    private String name;            //제목
    private String author;          //저자
    private String publisher;       //출판사
    private String topic;           //주제1
    private String topic2;          //주제2
    private Boolean borrow;         //대출유무
    private String save_name;       //저장이미지
    private Boolean favorites;      //즐겨찾기 유무

    private BorrowedBookDto borrowedBookDto;        // 책 대출정보(대출일자,반납일자 등)




}
