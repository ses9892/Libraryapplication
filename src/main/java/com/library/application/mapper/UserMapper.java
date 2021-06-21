package com.library.application.mapper;

import com.library.application.ResponseVo.RequestUser;
import com.library.application.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    void save(String test);

    void register(UserDto user);
}
