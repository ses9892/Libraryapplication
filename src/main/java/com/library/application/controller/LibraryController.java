package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.exception.BookNotFoundException;
import com.library.application.service.bookservice.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

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
    @GetMapping(value = "/book/{idx}")
    public String bookSelect(@PathVariable int idx,Model model){
        BookDto bookDto = Optional.ofNullable(bookService.selectByIdx(idx))
                .orElseThrow(()->new BookNotFoundException("<script>\n" +
                        "    alert(\"해당도서목록은 존재하지 않습니다!\");\n" +
                        "    history.back();\n" +
                        "</script>"));
        model.addAttribute("data",bookDto);
        return "bookselect";
    }
    @ResponseBody
    @GetMapping(value = "/book/{idx}/lend")
    public String booklend(@PathVariable int idx, Model model, HttpServletRequest request){

        return String.valueOf(idx);
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
