# Library-Project
### 도서관 프로젝트
***
#### 인원구성
- 장진호(개인)
#### 프로젝트 기간
- 21-06-16(수)  ~  2021-07-27(화)
#### 개요
- 객체 지향을 이해하며 Spring Boot 환경설정과 그에맞는 도서관 웹으로서의 기능을<br>
  &nbsp;&nbsp;&nbsp;&nbsp; 추가한다.</li>
- RestFul API 설계방식을 이해하며 요청,응답되는 Resource를 처리한다.
#### 목표
- Spring Boot와 객체지향을 이해하며 ,
  궁극적인 목표는 AWS를 이용한 배포환경을 설계한다.
#### 개발환경
- Tool : IntelliJ IDEA , STS , QueryBox
- Front : HTML , CSS , JS , BootStrap(3.xx) , JQuery
- Template : Thymeleaf
- Back : SpringBoot(2.3.11) , Mybatis , JWT
- DB : MySQL( AWS RDS )
- Server : AWS EC2
#### ERD
![img.png](img.png)
***
### Package
#### main
![img_2.png](img_2.png)
#### Resource
![img_3.png](img_3.png)
***
### 개발내역
- [환경설정](#환경설정)
- [인터셉터,JWT](#인터셉터,JWT)
- [ExceptionHandler](#ExceptionHandler)
- [Security](#Security)
- [jwt](#jwt)
- [Email](#Email)
- [Upload](#Upload)
- [SocketChating](#SocketChating)
- [AWS EC2](#EC2)
- [mybatis](#mybatis)
- [배포URL](#URL)
***
### ✨환경설정 
:one:[Application.yml](#cd-yml)

:two:[Controller](#cd-controller)

:three: [Service](#cd-service)

:four: [dto](#cd-dto)

---
### :cd: yml
- DB
<pre>
datasource:
    driver-class-name:
      com.mysql.cj.jdbc.Driver
    url:
      jdbc:mysql://gyguswlsgh.ciiiy3dtxwx4.ap-northeast-2.rds.amazonaws.com:1521/HyoHyunJinHo?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
    username:
      gyguswlsgh
    password:
      gyguswlsgh
</pre>
- Google Email Send
<pre>
  mail:
    host: smtp.gmail.com
    port: 587
    username: ses1238@gmail.com
    password: tdrrqd99
    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
</pre>
- Mybatis
<pre>
mybatis:
  type-aliases-package: com/library/application/ResponseVo/**
  mapper-locations: mapper/**.xml
</pre>
- JWT Token Config
<pre>
token:
  expiration_time: 864000000 #10 days
  secret: user_login
</pre>
- (Resource)Linux/window 10 & location URL
<pre>
#Linux & window  path
custom:
  path:
    upload-imges: 'D:/library/'
    upload-pdf: 'D:/pdf/'
  Linux:
    upload-imges: 'home/ec2-user/Library-Data/img'
    upload-pdf: 'home/ec2-user/Library-Data/pdf'
#URL location
  location:
    bookSave: /library/booksave
    bookReturn: /library/bookreturn
    userInfo: /user-service/user/info
    userDelete: /user-service/user/
    autoReturn: /user-service/user/auto_return
    bookSelect: /pdfview?file=
</pre>
---
### :cd: Controller

- [StartController](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/controller/StartController.java)
  - 로그인  & 회원기입 페이지 이동 로그인완료후 요청 URL 대한 컨트롤러
- [StartRestController](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/controller/StartRestController.java)
  - 회원가입 요청 & 중복체크 & 이메일인증 & 비밀번호찾기 요청에 대한 RestController
  
- [LibraryController](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/controller/LibraryController.java)
  - 도서관 서비스 이용에 대한 컨트롤러
  
- [UserServiceController](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/controller/UserServiceController.java)
  - User에 맞는 기능을 제공하는 요청을 받는 컨트롤러
  
- [ChatController](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/controller/ChatController.java)
  - 채팅 서비스에 요청을 받는 컨트롤러
---
### :cd: Service
- InterFace : UserService 
  - implement : [UserServiceImpl](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/service/UserServiceImpl.java)
  ![img_4.png](img_4.png)
- InterFace : BookService 
  - implement : [BookServiceImpl](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/service/bookservice/BookServiceImpl.java)
  ![img_5.png](img_5.png)
- InterFace : ChatService 
  - implement : [ChatServiceImpl](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/service/chatservice/ChatServiceImpl.java)
  ![img_6.png](img_6.png)
- Class : [MailService](https://github.com/ses9892/Libraryapplication/blob/master/src/main/java/com/library/application/service/MailService.java)
  <pre>
    //메일 인증에 관한 서비스
    @Service
    public class MailService {

      @Autowired
      public JavaMailSender javaMailSender;
  
      @Async
      public void sendMail(String email,String subject, String message) throws MessagingException {
          MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
  
          helper.setFrom("Library-Service");
          helper.setTo(email); //받는사람
          helper.setSubject(subject);
          helper.setText(message,true);
  
          javaMailSender.send(mimeMessage);
  
      }
  }
  </pre>
---
### :cd: dto
#### [DTO(Data Transfer Object)](https://github.com/ses9892/Libraryapplication/tree/master/src/main/java/com/library/application/dto) 는 계층간 데이터 교환을 위한 자바빈즈
![img_8.png](img_8.png)
- ROLE (유저별 권한을 지정하기위한 enum class)
<pre>
  @Getter
  @RequiredArgsConstructor
  public enum Role {
      //스프링 시큐리티에서 권한에 사용되는 클래스 , 무조건 ROLE을 붙여야함
  
      GUEST("ROLE_ADMIN", "운영자"),
      USER("ROLE_USER", "일반 사용자");
  
      public final String key;
      public final String title;
  }
</pre>



  





