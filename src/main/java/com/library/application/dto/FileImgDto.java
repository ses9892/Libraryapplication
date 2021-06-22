package com.library.application.dto;

import lombok.Data;

@Data
public class FileImgDto {
    /** 파일 번호 (PK) */
    private Long idx;

    /** 게시글 번호 (FK) */
    private Long boardIdx;

    /** 원본 파일명 */
    private String originalName;

    /** 저장 파일명 */
    private String saveName;

    /** 파일 크기 */
    private long size;
}
