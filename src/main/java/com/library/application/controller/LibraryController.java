package com.library.application.controller;

import com.library.application.ResponseVo.BookSaveRequest;
import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.ResponseVo.ResponseData;
import com.library.application.dto.BookDto;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
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


    @RequestMapping("/save/{userId}")
    public String bookSave(@PathVariable("userId")String userId,Model model){
        model.addAttribute("userId",userId);
        return "library/booksave";
    }

    //도서 등록페이지 이동
//    @RequestMapping(value = "/booksave/{userId/}")
    //restController 이동예정
    @ResponseBody
    @RequestMapping(value = "/save")
    public String savePage(HttpServletRequest request,Model model){
        String userId = ""+request.getAttribute("userId");
        return userId;
    }

    @ResponseBody
    @RequestMapping(value = "/return")
    public String returnPage(HttpServletRequest request){
        String userId = ""+request.getAttribute("userId");
        return userId;
    }

    //도서 대출 클릭시 페이지이동 + 모든 도서 열람
    @RequestMapping(value = "/list/{topic}",method = RequestMethod.GET)
    public String Librarylend(@PathVariable String topic,Model model){
        ResponseBookData responseBookData= bookService.selectAllBook(topic);
        model.addAttribute("bookList",responseBookData.getBookDtoList());
        return "library/booklend";
    }

    @GetMapping(value = "/book")
    public String bookSelect(HttpServletRequest request,Model model){
        BookDto bookDto = Optional.ofNullable(bookService.selectByIdx(Integer.parseInt(request.getParameter("idx"))))
                .orElseThrow(()->new BookNotFoundException("<script>\n" +
                        "    alert(\"해당도서목록은 존재하지 않습니다!\");\n" +
                        "    history.back();\n" +
                        "</script>"));
        model.addAttribute("data",bookDto);
        return "library/bookselect";
    }

    //Post 도서등록
    @PostMapping(value = "/book")
    public String saveBook(BookSaveRequest bookSaveRequest , MultipartFile[] files , Model model
                           , HttpServletResponse response) throws IOException {
        BookDto bookDto = new ModelMapper().map(bookSaveRequest,BookDto.class);
        boolean isSaved = bookService.saveBook(bookDto,files);
        if(isSaved==false){
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter pw = response.getWriter();
            pw.println("<script>alert('업로드한 확장자명을 확인해주세요'); history.go(-1);</script>");
            pw.flush();
        }
        return "library/booksave";
    }

    @ResponseBody
    @PostMapping(value = "/book/{idx}")
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

    //책삭제 delete "library/book"
    //책수정 PUT "library/book"


    //도서반납 -> 유저의 대출목록 가져오기
    @GetMapping(value = "/return/{userId}")
    public String returnBook(@PathVariable("userId") String userId,Model model){
        List<BookDto> list = bookService.selectBorrowedBookList(userId);
        model.addAttribute("list",list);
        model.addAttribute("now",new Date());
        return "library/bookreturn";
    }
    //도서반납OK
    @ResponseBody
//    @DeleteMapping(value = "/book/return/{idx}")
    @DeleteMapping(value = "/book/{idx}")
    public ResponseEntity<String> returnBookOK(@PathVariable("idx") int Book_idx, HttpServletRequest request,Model model){
        ResponseEntity<String> entity = null;
        //JWT 토큰 suject=userId 가 넘어온것을 받아온다.
        // userId + Book_idx 를 Service에 넘긴다.
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",""+request.getAttribute("userId"));
        hmap.put("book_idx",Book_idx);
        Boolean result = bookService.returnBook(hmap);
        if(result){
            entity = ResponseEntity.status(HttpStatus.OK).body("정상적으로 반납 완료 되었습니다!");
        }else{
            entity = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("error : BookIdx Not Found!!");
        }
        return entity;
    }
    //도서 연장 OK
    @ResponseBody
//    @PutMapping(value = "/book/return/{idx}")
    @PutMapping(value = "/book/{idx}")
    public ResponseEntity<String> extendBookOK(@PathVariable("idx") int Book_idx, HttpServletRequest request,Model model){
        ResponseEntity<String> entity = null;
        //JWT 토큰 suject=userId 가 넘어온것을 받아온다.
        // userId + Book_idx 를 Service에 넘긴다.
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",""+request.getAttribute("userId"));
        hmap.put("book_idx",Book_idx);
        Boolean result = bookService.extendBook(hmap);
        if(result){
            entity = ResponseEntity.status(HttpStatus.OK).body("정상적으로 연장 완료 되었습니다!");
        }else{
            entity = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("error : BookIdx Not Found!!");
        }
        return entity;
    }

    //도서 조회
    @ResponseBody
    @GetMapping(value = "/book/{idx}")
    public ResponseEntity<String> selectBook(@PathVariable("idx") int Book_idx , Model model , HttpServletRequest request){

        //fileName을 넘겨주는 식으로 간다?
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",""+request.getAttribute("userId"));
        hmap.put("book_idx",Book_idx);
        String fileName = bookService.selectPdfFileName(hmap);
        if(fileName==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록되지 않거나 대출받지 않은 도서를 조회할 수 없습니다.");
        }
        String url = env.getProperty("custom.location.bookSelect")+"/img/"+fileName;
        return ResponseEntity.status(HttpStatus.OK).body(url);
    }



    @ResponseBody
    @PutMapping(value = "/book/favorites/{idx}")
    public ResponseEntity<String> favoritesBook(@PathVariable("idx") int Book_idx, HttpServletRequest request,Model model){
        ResponseEntity<String> entity = null;
        //JWT 토큰 suject=userId 가 넘어온것을 받아온다.
        // userId + Book_idx 를 Service에 넘긴다.
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",""+request.getAttribute("userId"));
        hmap.put("book_idx",Book_idx);
        Boolean result = bookService.favoritesBook(hmap);
        if(result){
            entity = ResponseEntity.status(HttpStatus.OK).body("Favorites Add OK!");
        }else{
            entity = ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("error : BookIdx Not Found!!");
        }
        return entity;
    }



}
