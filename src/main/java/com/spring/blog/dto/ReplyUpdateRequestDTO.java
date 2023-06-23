package com.spring.blog.dto;

import com.spring.blog.entity.Reply;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ReplyUpdateRequestDTO {
    private long replyId;
    private String replyContent;

    public ReplyUpdateRequestDTO(Reply reply){
        this.replyId = reply.getReplyId();
        this.replyContent = reply.getReplyContent();
    }
}
