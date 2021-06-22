package com.library.application.mapper;

import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    //파일이미지를 DB에 등록
    public int insertBookImg(List<FileImgDto> fileImgDtoList);

    public void insertBook(BookDto bookDto);

    int selectByIdx(BookDto bookDto);
}
