package com.spring.blog.repository;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {
    List<ReplyResponseDTO> findAllByBlogId(long blogId);

    ReplyResponseDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyCreateRequestDTO replyInsertDTO);

<<<<<<< HEAD
    void update(ReplyUpdateRequestDTO replyUpdateDTO);

    void deleteAllByBlogId(long blogId);
}
=======
    void update(ReplyUpdateDTO replyUpdateDTO);

    void deleteAllByBlogId(long blogId);
}
>>>>>>> b5d6438566d48edc3c9eff330c6c15ae29cbbb55
