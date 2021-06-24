package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import com.library.application.dto.FileImgDto;
import com.library.application.mapper.BookMapper;
import com.library.application.util.FIleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BookServiceImpl implements BookService{

    private FIleUtils fIleUtils;

    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(FIleUtils fIleUtils, BookMapper bookMapper) {
        this.fIleUtils = fIleUtils;
        this.bookMapper = bookMapper;
    }

    @Override
    public boolean saveBook(BookDto bookDto, MultipartFile[] files) {
        int result =1;
        bookDto.setUserId("admin");
        //책 정보 저장
        bookMapper.insertBook(bookDto);
        //책번호 가져오기
       long book_idx =  bookMapper.selectByIdx(bookDto);
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
    //책의 주제를 받고 주제에관한 데이터를 가져오는 메소드

}
