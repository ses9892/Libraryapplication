package com.library.application.service.bookservice;

import com.library.application.ResponseVo.ResponseBookData;
import com.library.application.dto.BookDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

//도서 등록 & 삭제 & 조회 & 상세조회 & 연장 & 반납 & 즐겨찾기 에 대한 메소드를 구현하는 인터페이스
public interface BookService {

    public boolean saveBook(BookDto bookDto, MultipartFile[] files);

    public ResponseBookData selectAllBook(String topic);

    BookDto selectByIdx(int idx);

    Boolean lendBook(int book_idx, HttpServletRequest request);

    void AutoBookReturn();

    List<BookDto> selectBorrowedBookList(String userId);

    Boolean returnBook(HashMap<String, Object> hmap);

    Boolean extendBook(HashMap<String, Object> hmap);

    Boolean favoritesBook(HashMap<String, Object> hmap);

    String selectPdfFileName(HashMap<String, Object> hmap);
}
