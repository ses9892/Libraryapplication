package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.service.bookservice.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
@RequestMapping(value = "/library")
public class LibraryController {
    @Autowired
    Environment env;

    @Autowired
    BookService bookService;

    //도서 등록페이지 이동
    @RequestMapping(value = "/book")
    public String Librarysave(){

        return "booksave";
    }

    //도서 대출 클릭시 페이지이동 + 모든 도서 열람
    @RequestMapping(value = "/booklend/{topic}")
    public String Librarylend(@PathVariable String topic,Model model){
        ResponseBookData responseBookData= bookService.selectAllBook(topic);
        model.addAttribute("bookList",responseBookData.getBookDtoList());
        return "booklend";
    }


    //Post 도서등록
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
