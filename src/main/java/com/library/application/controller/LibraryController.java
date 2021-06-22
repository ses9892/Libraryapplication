package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.dto.BookDto;
import com.library.application.service.bookservice.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping(value = "/library")
public class LibraryController {

    @Autowired
    BookService bookService;

    @RequestMapping(value = "/book")
    public String LibraryHome(){

        return "booksave";
    }

    @PostMapping(value = "/book")
    public String saveBook(BookSaveRequest bookSaveRequest , MultipartFile[] files , Model model){
        BookDto bookDto = new ModelMapper().map(bookSaveRequest,BookDto.class);
        boolean isSaved = bookService.saveBook(bookDto,files);
        if(isSaved==false){
            return "failed";
        }

        return "booksave";
    }

}
