package com.library.application.service.bookservice;

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
}
