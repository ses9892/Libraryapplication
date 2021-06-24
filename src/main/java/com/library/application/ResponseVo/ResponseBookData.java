package com.library.application.ResponseVo;

import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookData {

    private List<BookDto> bookDtoList;


}
