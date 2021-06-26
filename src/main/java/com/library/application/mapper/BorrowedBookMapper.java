package com.library.application.mapper;

import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Mapper
@Repository
public interface BorrowedBookMapper {

    void borrow(HashMap<String, Object> hmap);
}
