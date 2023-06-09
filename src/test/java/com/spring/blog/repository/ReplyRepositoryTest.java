package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    @DisplayName("2번 글에 연동된 댓글 개수가 4개인지 확인")
    public void findAllByBlogIdTest(){
        int blogId = 2;

        List<ReplyFindByIdDTO> replyList = replyRepository.findAllByBlogId(blogId);

        assertEquals(3, replyList.size());
    }

    @Test
    @Transactional
    public void findByReplyIdTest(){
        long replyId = 3;

        ReplyFindByIdDTO reply = replyRepository.findByReplyId(replyId);

        assertNotNull(reply);
        assertEquals("we",reply.getReplyWriter());
    }
}
