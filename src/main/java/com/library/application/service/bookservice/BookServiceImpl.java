package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.dto.BorrowedBookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.dto.UserDto;
import com.library.application.exception.BookNotFoundException;
import com.library.application.mapper.BookMapper;
import com.library.application.mapper.BorrowedBookMapper;
import com.library.application.mapper.UserMapper;
import com.library.application.util.FIleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.print.Book;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
//    @Scheduled(fixedRate = 10000) // 1초 마다 실행
    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void AutoBookReturn() {      // 1일기준 00시00분 자동실행되는 도서 자동반납메소드
        //Borrowed_book -> 반납일자가 오늘날짜인 (도서의 번호+빌린유저번호) select-> List<HasMap>
        //index 당 book_idx , userIdx의 key를 가진 list
        List<BorrowedBookDto> list = borrowedBookMapper.autoBookReturn();
        log.info(list.toString());
        if(list.size()>0){
        //Borrowed_book 해당데이터삭제
        borrowedBookMapper.deleteByBookIdxList(list);
        bookMapper.autoBookReturn(list);
        userMapper.autoBookReturn(list);
        //book_idx 와 userIdx로 반납
        }
        int size = list.size();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        log.info(size+"개의 책이"+sdf.format(new Date(System.currentTimeMillis()))+" 반납 완료되었습니다.");
    }
    //책의 주제를 받고 주제에관한 데이터를 가져오는 메소드

    //도서 반납 -> userId의 대출한목록을 되돌려줄 메소드
    @Override
    public List<BookDto> selectBorrowedBookList(String userId) {
        //userId를 이용해 userId가 빌린 책번호를 list에 담아온다.
        List<Integer> BookIdxList = borrowedBookMapper.selectBorrowedBookList(userId);
        //대출내역 없을시 핸들링
        if(BookIdxList.size()==0){
            throw new BookNotFoundException("<script>alert('대출내역이 존재하지 않습니다.'); history.back();</script>");
        }
        List<BookDto> bookDtoList = bookMapper.selectByIdxList(BookIdxList);
        return bookDtoList;
    }

    @Override
    public Boolean returnBook(HashMap<String, Object> hmap) {
        //userId, book_idx
        //현재 대출중인 책인지 조회 ( borrowed_book에서 해당 book_idx에 정보를 가져온다.)
        //왜 이렇게 햇나? = 자동반납시스템과 겹쳐 null을 반환할경우를 대비함.
       BorrowedBookDto dto =  Optional.ofNullable(borrowedBookMapper.selectBorrowedBook(hmap))
        .orElseThrow(()->new BookNotFoundException("해당 도서는 현재 대출중인 도서가 아닙니다!"));
        //Borrowed_book의 데이터를 삭제한다.
        borrowedBookMapper.deleteByBookIdx(hmap);
        //Book의 borrow를 false로 돌린다.
        bookMapper.borrowBook(hmap);
        //UserId의 대출한 도서 량을 수정한다.
        userMapper.borrowBook(hmap);
        return true;

    }
}
