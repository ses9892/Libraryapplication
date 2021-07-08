package com.library.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

    /** 확장자 */
    private String extenstion;

}
