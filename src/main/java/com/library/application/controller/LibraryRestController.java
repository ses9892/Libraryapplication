package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.dto.BookDto;
import com.library.application.service.bookservice.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/check")
public class LibraryRestController {

    @Autowired
    BookService bookService;
    @Autowired
    Environment env;

    //토큰 검사 -> 도서 등록 AJAX -> LibraryController
    //토큰 검사 -> 도서 반납 AJAX -> LibraryController
    @GetMapping(value = "/book")
    public String LibrarySave(HttpServletRequest request){
        String flag = (String) request.getAttribute("flag");
        String url = env.getProperty("custom.location."+flag);
        url+="/"+request.getAttribute("userId");
        /** ex : /library/bookSave/**(userId)      */
        return url;
    }
}
