package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReplyUpdateDTO {
    private long replyId;
    private String replyContent;

    public ReplyUpdateDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyContent = reply.getReplyContent();
    }
}
