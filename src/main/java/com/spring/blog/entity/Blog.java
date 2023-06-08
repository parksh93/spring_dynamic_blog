package com.spring.blog.entity;

import lombok.*;

import java.sql.Date;

// 역직렬화(DB -> 자바객체) 가 가능하도록 blog 테이블 구조에 맞게 멤버변수 선언
// 패러다임 불일치 해결
@Getter
@Setter   // 데이터 불변성을 위해 엔터티에는 setter를 작서앟지 않는다.
            // 생성자를 통해 처음 값이 들어간 후 변경이 없어야 한다.
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder   //빌더패턴 생성자(전체 생성자가 있어야 된다)
public class Blog {
    private long blogId;
    private String writer;
    private String blogTitle;
    private String blogContent;
    private Date publishedAt;
    private Date updatedAt;
    private int blogCount;
}
