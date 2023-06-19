package com.spring.blog.repository;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.dto.ReplyInsertDTO;
import com.spring.blog.dto.ReplyUpdateDTO;
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

    @Test
    @Transactional
    public void deleteByReplyIdTest(){
        long replyId = 2;
        int blogId = 2;

        replyRepository.deleteByReplyId(replyId);
        List<ReplyFindByIdDTO> replyList = replyRepository.findAllByBlogId(blogId);
        ReplyFindByIdDTO reply = replyRepository.findByReplyId(replyId);

        assertEquals(2, replyList.size());
        assertNull(reply);
    }

    @Test
    @Transactional
    public void saveTest(){
        long blogId = 2;
        String replyWriter = "추가작성자";
        String replyContent = "안녕하세요 추가로 작성했어요";
        ReplyInsertDTO replyInsertDTO = ReplyInsertDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyRepository.save(replyInsertDTO);
        List<ReplyFindByIdDTO> replyList = replyRepository.findAllByBlogId(blogId);

        assertEquals(4, replyList.size());
        assertEquals(replyWriter, replyList.get(replyList.size()-1).getReplyWriter());
        assertEquals(replyContent, replyList.get(replyList.size()-1).getReplyContent());
    }

    @Test
    @Transactional
    public void updateTest(){
        long replyId = 3;
        String replyContent = "수정했어요";
        ReplyUpdateDTO replyUpdateDTO = ReplyUpdateDTO.builder()
                            .replyId(replyId)
                            .replyContent(replyContent)
                            .build();

        replyRepository.update(replyUpdateDTO);
        ReplyFindByIdDTO reply = replyRepository.findByReplyId(replyId);

        assertEquals(replyContent, reply.getReplyContent());
        assertNotEquals(reply.getPublishedAt(), reply.getUpdatedAt());
        // updatedAt이 publishedAt보다 이후시점(after)이다
        assertTrue(reply.getUpdatedAt().isAfter(reply.getPublishedAt()));

    }
}
