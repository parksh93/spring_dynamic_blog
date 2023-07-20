package com.spring.blog.config.jwt;

import com.spring.blog.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor // 멤버변수를 이용한 생성자 자동 생성
public class TokenProvider {
    private final JwtProperties jwtProperties;

    // 최종적으로 완성된 토큰을 외부에서 전달받도록 만들어주는 메서드
    public String generateToken(User user, Duration expiredAt){
        Date now = new Date();
        return  makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 내부적으로 토큰 생성 *
    private String makeToken(Date expiry, User user){
        // Jwts.builder()를 활용한 토큰 발급
        Date now = new Date(); //현재 시간으로 토큰 발급 날짜 저장

        return Jwts.builder() //토큰생성
                                // (key, value)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)   // 헤더 타입은 JWT
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) //발급시간
                .setExpiration(expiry)  //만기시각
                .setSubject(user.getLoginId())  //기본적으로는 아이디를 준다
                .claim("id", user.getId())  // 클레임에는 PK제공
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())   //알고리즘과 비밀키 같이 입력
                .compact(); //생성 메서드는 빌더패턴이지만 .build()가 아닌 .compact() 사용
    }

    // 토큰의 유효성 검증 *
    // 발급된 토큰을 입력받았을때, 유효하면 true, 아니면 false 리턴
    public boolean validToken(String token) {
        // Jwts.parser()를 이용한 토큰 파싱 및 검사
        try {
            Jwts.parser()   // 토큰을 먼저 파싱
                    .setSigningKey(jwtProperties.getSecretKey())    // 복호화에는 암호화때 사용한 비밀키 입력
                    .parseClaimsJws(token); //검증 타겟이 되는 토큰 입력
            return true;
        }catch (Exception e){
            return false;   // 파싱 및 복호화 문제가 발생하면 익셉션이 발생
        }
    }

    // 토큰 기반으로 인증 정보를 리턴
    public Authentication getAuthentication(String token) {
        // 토큰을 입력하면 로그인한 유저 정보를 리턴함
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities =
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(claims.getSubject(),
                            "",authorities
                ),
                token, authorities
        );
    }

    // 토큰 기반으로 유저번호를 가져오게 하는 메서드
    public Long getUserId(String token){
        // 토큰으로 클레임 정보를 얻어오고, 거기서 id를 리턴
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 토큰 입력시 클레임을 리턴하도록 해 주는 메서드
    private Claims getClaims(String token){
        // 토큰으로 클래임 조회
        return  Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey()) // 비밀키를 제공하고
                .parseClaimsJws(token)  // 토큰을 제공하면
                .getBody(); // 유저 정보를 역으로 리턴
    }
}
