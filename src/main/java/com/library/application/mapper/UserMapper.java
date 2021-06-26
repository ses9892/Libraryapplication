package com.library.application.mapper;

import com.library.application.ResponseVo.RequestUser;
import com.library.application.dto.BorrowedBookDto;
import com.library.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserMapper {
    void save(String test);

    void register(UserDto user);

    UserDto findByUserId(HashMap<String,Object> hashMap);

    int duplication(String idCheck);

    void borrowBook(HashMap<String, Object> hmap);

    void autoBookReturn(List<BorrowedBookDto> list);
}
