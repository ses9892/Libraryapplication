package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    public boolean saveBook(BookDto bookDto, MultipartFile[] files);

    public ResponseBookData selectAllBook(String topic);

    BookDto selectByIdx(int idx);
}
