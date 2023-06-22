package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyRepository {
    List<ReplyFindByIdDTO> findAllByBlogId(long blogId);

    ReplyFindByIdDTO findByReplyId(long replyId);

    void deleteByReplyId(long replyId);

    void save(ReplyInsertDTO replyInsertDTO);

    void update(ReplyUpdateDTO replyUpdateDTO);

    void deleteAllByBlogId(long blogId);
}
