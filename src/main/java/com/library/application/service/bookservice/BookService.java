package com.library.application.service.bookservice;

import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

    public boolean saveBook(BookDto bookDto, MultipartFile[] files);
}
