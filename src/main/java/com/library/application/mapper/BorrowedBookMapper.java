package com.library.application.mapper;

import com.library.application.dto.BookDto;
import com.library.application.dto.BorrowedBookDto;
import com.library.application.dto.FileImgDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BorrowedBookMapper {

    void borrow(HashMap<String, Object> hmap);

    List<BorrowedBookDto> autoBookReturn();

    void deleteByBookIdxList(List<BorrowedBookDto> list);

    List<Integer> selectBorrowedBookList(String userId);
}
