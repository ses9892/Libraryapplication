package com.library.application.service;

import com.library.application.dto.Role;
import com.library.application.dto.UserDto;
import com.library.application.exception.BookNotFoundException;
import com.library.application.exception.UserLoginErrorException;
import com.library.application.exception.UserNotDeleteException;
import com.library.application.mapper.BookMapper;
import com.library.application.mapper.BorrowedBookMapper;
import com.library.application.mapper.UserMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserMapper userMapper;

    BookMapper bookMapper;

    BorrowedBookMapper borrowedBookMapper;

    Environment env;

    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserMapper userMapper, BookMapper bookMapper,
                           BorrowedBookMapper borrowedBookMapper, Environment env,
                           BCryptPasswordEncoder passwordEncoder ) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.borrowedBookMapper = borrowedBookMapper;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void save(String test) {
        userMapper.save(test);
    }
    //회원가입
    @Override
    public Boolean register(UserDto user) {
        //user password 암호화 해서 꼭 저장하기!
        user.setPwd(passwordEncoder.encode(user.getPwd()));
        user.setRole(Role.USER.getKey());
        try{
        userMapper.register(user);
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
        return true;
    }
    //Login 요청 -> 검사 -> 토큰발급
    @Override
    public String login(HashMap<String, Object> requestLogin) {
        UserDto userDto = userMapper.findByUserId(requestLogin);
        //로그인 실패시 null 리턴
        if(userDto==null){      // 회원정보가 없을시
            return null;
        }else if(!passwordEncoder.matches((String)requestLogin.get("password"),userDto.getPwd())){    //비밀번호 일치하지 않을경우
            return null;
        }
        //로그인 성공시 토큰 발급
       String Token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret")).compact();
        return Token;
    }

    @Override
    public String duplication(String idCheck) {
        // idCheck의 중복갯수
        int Cnt = userMapper.duplication(idCheck);
        if(Cnt ==0){
            return "true";
        }
        return "false";
    }

    @Override
    public UserDto pwdCheck(HashMap<String, Object> hmap) {
        UserDto dto = userMapper.findByUserId(hmap);
        if(!passwordEncoder.matches((CharSequence) hmap.get("pwd"),dto.getPwd())){
            return null;
        }
        return dto;
    }

    @Override
    public UserDto selectUserId(String userId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId);
        UserDto userDto = userMapper.findByUserId(hashMap);
        return userDto;
    }

    @Override
    public Boolean updateUser(UserDto userDto) {
        HashMap hashMap = new HashMap();
        hashMap.put("userId", userDto.getUserId());
        UserDto dto = Optional.ofNullable(userMapper.findByUserId(hashMap))
                .orElseThrow(()-> new UserLoginErrorException("존재하지 않는 회원입니다."));
        if(userDto.getPwd()!=null) {
            userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));
        }
        userMapper.updateUserDate(userDto);
        return true;
    }

    //회원탈퇴
    @Override
    public void deleteUser(String userId) {
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId);
//        1. 빌린 book 조회 및 반납
        try{
        List<Integer> bookIdxList = borrowedBookMapper.selectBorrowedBookList(userId);  //조회
        borrowedBookMapper.deleteByUserId(hashMap);                                     //userId 비교 후 정보삭제

        if(bookIdxList.size()!=0){                                                      //list가 아무것도 안들어있으면 에러가난다.
        bookMapper.bookReturn(bookIdxList);                                             //대출받은 bookList 반납완료
        }
//        2. 유저가 등록한 book 삭제
        bookMapper.deleteByUserId(hashMap);
//        3. 회원테이블 해당유저삭제
        userMapper.deleteByUserId(hashMap);
        }catch (Exception e){
            throw new UserNotDeleteException("회원을 탈퇴 할 수 없습니다. 관리자에게 문의해 주세요");
        }


    }
    //UserDetail 인증
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",s);
        UserDto dto = userMapper.findByUserId(hmap);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(dto.getRole()));
        return new User(dto.getUserId(),dto.getPwd(),authorities);
        //리턴된 데이터(유저)는 SecurityContext 의 Authentication에 등록되어 인증정보를 갖춘다.
    }
}
