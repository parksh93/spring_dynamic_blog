package com.spring.blog.entity;

import lombok.*;

import java.sql.Date;
import java.time.LocalDateTime;

//Entity는 불변성을 지키기 위해서 Setter를 제공하지 않는다.
// 한번 생성된 데이터가 변경될 가능성을 없앤다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reply {
    private long replyId;
    private long blogId;
    private String replyWriter;
    private String replyContent;
    private LocalDateTime publishedAt;
    private LocalDateTime updatedAt;
}
