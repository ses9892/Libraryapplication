package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.exception.BookNotFoundException;
import com.library.application.mapper.BookMapper;
import com.library.application.mapper.BorrowedBookMapper;
import com.library.application.mapper.UserMapper;
import com.library.application.util.FIleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    private FIleUtils fIleUtils;

    private BookMapper bookMapper;

    private UserMapper userMapper;

    private BorrowedBookMapper borrowedBookMapper;

    @Autowired
    public BookServiceImpl(FIleUtils fIleUtils, BookMapper bookMapper,UserMapper userMapper,BorrowedBookMapper borrowedBookMapper) {
        this.fIleUtils = fIleUtils;
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
        this.borrowedBookMapper = borrowedBookMapper;
    }

    @Override
    public boolean saveBook(BookDto bookDto, MultipartFile[] files) {
        int result =1;
        bookDto.setUserId("admin");
        //책 정보 저장
        bookMapper.insertBook(bookDto);
        //책번호 가져오기
       long book_idx =  bookMapper.selectBookIdx(bookDto);
        //가져온 책번호로 파일명,책번호 이미지 저장 + 경로에 이미지 업로드 (정보가 List<dto>로 들어옴)
        List<FileImgDto> fileList = fIleUtils.uploadFiles(files,book_idx);

        //List가 비었는지 확인한다. 비어있지 않다면
        if(CollectionUtils.isEmpty(fileList)==false){
            result = bookMapper.insertBookImg(fileList);
            //저장
            if(result<1){
                result=0;
            }
        }
        return (result>0);
    }

    //전체 책을 가져오는 메소드
    @Override
    public ResponseBookData selectAllBook(String topic) {
        //책정보 + 책번호에 맞는 이미지
            ResponseBookData responseBookData = new ResponseBookData();
        //전체책 정보
//        if(topic.equals("all")) {
//            responseBookData.setBookDtoList(bookMapper.selectAll());
//            //UUID 변환할 필요 없음!
//            return  responseBookData;
//        }
        //대출 불가능책 정보
//        if(topic.equals("not")){
//            responseBookData.setBookDtoList(bookMapper.notborrow());
//        }
        //주제에맞는 책정보
//        responseBookData.setBookDtoList(bookMapper.selectByTopic(topic));

        //합친것
        responseBookData.setBookDtoList(bookMapper.selectAll(topic));
        return responseBookData;
    }

    @Override
    public BookDto selectByIdx(int idx) {
        BookDto bookDto = null;
        bookDto = bookMapper.selectByIdx(idx);
        return bookDto;
    }

    @Override
    public Boolean lendBook(int book_idx, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");

        //책정보가져오기 --> 빌린지 안빌린지 비교
        BookDto bookDto = Optional.ofNullable(bookMapper.selectByIdx(book_idx))
                            .orElseThrow(()->new BookNotFoundException("해당 도서를 찾을 수 없습니다."));

        if(bookDto.getBorrow()){
            return false;
        }
        //HashMap 정보 등록
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",userId);
        hmap.put("book_idx",book_idx);
        //대출 목록 테이블 insert
        try{
        borrowedBookMapper.borrow(hmap);
        //대출한 목록에서 해당도서가 있으면  유저borrowed_book +1   ,  책 대출유무 변경
        userMapper.borrowBook(hmap);        // user의 현재까지 책 빌린 횟수를 변경시킨다. (borrewed_book에 userId가 있는만큼 변경 (증가 X)
        bookMapper.borrowBook(hmap);        // book의 borrow의 유무를 변경한다. (if borrowed_book에 해당 책번호가 존재하지않으면)
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
    //책의 주제를 받고 주제에관한 데이터를 가져오는 메소드

}
