package com.library.application.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtTokenProvider {
    private String secretKey;   //Token 키 내용
    private long validityInMilliseconds;    //Token의 유지시간

    //@Value() = 매개변수에사용되며 해당매개변수는 JwtTokenProvider의 생성자가 실행될때
    //application.yml 환경설정파일의 적어둔 내용을 읽어서 매개변수에 저장한다.
    public JwtTokenProvider(@Value("${token.secret}") String secretKey, @Value("${token.expiration_time}") long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    //토큰생성
    public String createToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);

        Date now = new Date();

        Date validity = new Date(now.getTime()
                + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰에서 값(제목) 추출
    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            String subject = this.getSubject(token);
            if (claims.getBody().getSubject()==null || claims.getBody().getSubject().isEmpty()) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
