package com.spring.blog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter //Entity에 setter는 쓰지 않는것이 좋다
@NoArgsConstructor
@Table(name = "users")  //만약 클래스명과 테이블 명이 다르게 매칭되기를 원하면 사용하는 어노테이션. USER는 Mysql 예약어
public class User implements UserDetails { //UserDetails의 구현체만 스프링 시큐리티에서 인증정보로 사용할 수 있다.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)  // 수정 불가 옵션
    private Long id;  // null 체크 대비위해 참조형으로 변수 선언

    @Column(nullable = false, unique = true)    // notNull, 유일키
    private String email;

    @Column(nullable = false, unique = true)
    private String loginId;

    // 비밀번호 null 허용(OAuth2.0을 활용한 소셜로그인은 비밀번호가 없음)
    private String password;

    @Builder
    public User(String email, String password, String loginId, String auth){    //auth : 인증 정보 요구
        this.email = email;
        this.password = password;
        this.loginId = loginId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user")); // 운영자라는 권한이 있을 때 운영자 권한만 주는것이 아닌 정회원, 준회원 등의 권한도 모두 다 주기 떄문에 List 형태로 받아온다
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {   //로그인에 사용할 아이디 (unique 요소만 가능)
        return loginId;
    }

    @Override
    public boolean isAccountNonExpired() {  // 로그인 중이면 true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {   // 밴당하지 않았다면 true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
