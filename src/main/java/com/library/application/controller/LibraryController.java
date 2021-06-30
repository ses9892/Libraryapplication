package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.dto.UserDto;
import com.library.application.exception.BookNotFoundException;
import com.library.application.service.bookservice.BookService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
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
    @RequestMapping(value = "/booksave/{userId}")
    public String Librarysave(@PathVariable("userId") String userId,Model model){
        model.addAttribute("userId",userId);
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
    public String booklend(@PathVariable("idx") int book_idx, Model model, HttpServletRequest request){
        //책번호 , userId를 받은상태 -> 대출해주기
        //1. 유저정보 borrowed_book++
        //2. book_idx 책의 borrow -> 1로
        //3. Borrowed_book db 정보추가
        if(bookService.lendBook(book_idx,request)) {
            return "정상적으로 대출 되었습니다.";
        }
        return "현재 미 반납 도서이거나,이미 대출하신 도서입니다.";
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

    //도서반납 -> 유저의 대출목록 가져오기
    @GetMapping(value = "/bookreturn/{userId}")
    public String returnBook(@PathVariable("userId") String userId,Model model){
        List<BookDto> list = bookService.selectBorrowedBookList(userId);
        model.addAttribute("list",list);
        model.addAttribute("now",new Date());
        return "bookreturn";
    }
    @ResponseBody
    @DeleteMapping(value = "/book/return/{idx}")
    public ResponseEntity<String> returnBookOK(@PathVariable("idx") int Book_idx, HttpServletRequest request,Model model){
        //JWT 토큰 suject=userId 가 넘어온것을 받아온다.
        String userId = ""+request.getAttribute("userId");
        // userId + Book_idx 를 Service에 넘긴다.

        return ResponseEntity.status(HttpStatus.OK).body("");
    }



}
