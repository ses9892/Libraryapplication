package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.dto.BookDto;
import com.library.application.service.bookservice.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/check")
public class LibraryRestController {

    @Autowired
    BookService bookService;

    @GetMapping(value = "/book")
    public String LibrarySave(){
        String url = "/library/book";
        return url;
    }
}
