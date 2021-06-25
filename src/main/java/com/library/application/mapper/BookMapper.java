package com.library.application.mapper;

import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BookMapper {

    //파일이미지를 DB에 등록
    public int insertBookImg(List<FileImgDto> fileImgDtoList);

    public void insertBook(BookDto bookDto);

    int selectBookIdx(BookDto bookDto);

    List<BookDto> selectAll(@Param("flag") String flag);

    BookDto selectByIdx(int idx);
//    List<BookDto> selectByTopic(String topic);
//
//    List<BookDto> notborrow();
//
//    List<BookDto> test(String flag);
}
