package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface BookService {

    public boolean saveBook(BookDto bookDto, MultipartFile[] files);

    public ResponseBookData selectAllBook(String topic);

    BookDto selectByIdx(int idx);

    Boolean lendBook(int book_idx, HttpServletRequest request);
}
