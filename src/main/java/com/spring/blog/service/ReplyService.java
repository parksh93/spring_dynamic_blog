package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;

import java.util.List;

public interface ReplyService {
    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    ReplyResponseDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyCreateRequestDTO replyInsertDTO);

    void update(ReplyUpdateRequestDTO replyUpdateDTO);
}
