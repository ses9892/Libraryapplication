package com.library.application.service;

import com.library.application.dto.Role;
import com.library.application.dto.UserDto;
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

import javax.mail.MessagingException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    UserMapper userMapper;

    BookMapper bookMapper;

    BorrowedBookMapper borrowedBookMapper;

    Environment env;

    BCryptPasswordEncoder passwordEncoder;

    MailService mailSender;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, BookMapper bookMapper,
                           BorrowedBookMapper borrowedBookMapper, Environment env,
                           BCryptPasswordEncoder passwordEncoder, MailService mailSender) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.borrowedBookMapper = borrowedBookMapper;
        this.env = env;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
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

    @Override
    public String emailSend(String email) throws MessagingException{
        String encryptKey = certified_key();
        String flag = "emailSend";
        String buffer = emailSendBuffer(encryptKey,flag);
        mailSender.sendMail(email,"[Library 이메일 인증]",buffer.toString());
        return encryptKey;
    }

    @Override
    public String forgetPwd(HashMap<String, Object> data)throws MessagingException {
        UserDto dto = Optional.ofNullable(userMapper.findByUserId(data))
                .orElseThrow(()-> new UserLoginErrorException("해당 ID를 찾을수 없습니다."));
        //새로운 임시비밀번호를 발솔할 userId의 email
        String email = dto.getEmail();
        //새로운 패스워드
        String newPwd = certified_key();
        data.put("pwd",passwordEncoder.encode(newPwd).toString());
        //새로운 패스워드 -> 암호화 -> update sql
        userMapper.updateUserPwd(data);
        String flag = "forgetPwd";
        String buffer = emailSendBuffer(newPwd,flag);
        mailSender.sendMail(email,"[Library 임시비밀번호 발급]",buffer.toString());
        return email;
    }

    //UserDetail 인증
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        HashMap<String,Object> hmap = new HashMap<>();
        hmap.put("userId",s);
        UserDto dto = userMapper.findByUserId(hmap);
        if(dto==null){
            throw new UsernameNotFoundException("해당 아이디는 존재하지 않습니다.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(dto.getRole()));
        return new User(dto.getUserId(),dto.getPwd(),authorities);
        //리턴된 데이터(유저)는 SecurityContext 의 Authentication에 등록되어 인증정보를 갖춘다.
    }
    private String certified_key() {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int num = 0;

        do {
            num = random.nextInt(75) + 48;
            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }

        } while (sb.length() < 10);
        return sb.toString();
    }
    private String emailSendBuffer(String encryptKey, String flag){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<!DOCTYPE html>");
        buffer.append("<html>");
        buffer.append("<head>");
        buffer.append("</head>");
        buffer.append("<body>");
        if(flag.equals("emailSend")) {
            buffer.append(" <div" +
                    "style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                    "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                    "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Library</span><br />" +
                    "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." +
                    "	</h1>\n" +
                    "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                    "		Library에 오신걸 환영합니다.<br />" +
                    "		아래 <b style=\"color: #02b875\">'암호'</b> 를 보시고 회원가입 이메일 인증을 완료해 주세요.<br />" +
                    "		감사합니다." +
                    "	</p>" +
                    "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                    "	target=\"_blank\">" +
                    "		<p" +
                    "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                    "			" + encryptKey + "</p>" +
                    "	</a>" +
                    "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                    " </div>");
        }else if(flag.equals("forgetPwd")){
            buffer.append(" <div" +
                    "style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                    "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                    "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">Library</span><br />" +
                    "		<span style=\"color: #02b875\">임시비밀번호</span> 안내입니다." +
                    "	</h1>\n" +
                    "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                    "		Library에 오신걸 환영합니다.<br />" +
                    "		아래 <b style=\"color: #02b875\">'임시암호'</b> 를 보시고 로그인하여 주십시오<br />" +
                    "		감사합니다." +
                    "	</p>" +
                    "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\"" +
                    "	target=\"_blank\">" +
                    "		<p" +
                    "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">" +
                    "			" + encryptKey + "</p>" +
                    "	</a>" +
                    "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                    " </div>");
        }
        buffer.append("</body>");
        buffer.append("</html>");
        return buffer.toString();
    }

}
