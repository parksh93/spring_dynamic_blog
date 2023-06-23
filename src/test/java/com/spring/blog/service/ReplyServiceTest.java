package com.spring.blog.service;

import com.spring.blog.dto.ReplyResponseDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;
import com.spring.blog.dto.ReplyUpdateRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest //통합테스트용
public class ReplyServiceTest {
    @Autowired
    ReplyService replyService;

    @Test
    @Transactional
    public void findAllByBlogIdTest(){
        long blogId = 2;

        List<ReplyResponseDTO> replyList = replyService.findAllByBlogId(blogId);

        assertEquals(3, replyList.size());
    }

    @Test
    @Transactional
    public void findByReplyIdTest(){
        long replyId = 3;

        ReplyResponseDTO reply = replyService.findByReplyId(replyId);

        assertEquals("we",reply.getReplyWriter());
        assertEquals(3,reply.getReplyId());
    }

    @Test
    @Transactional
    public void deleteByReplyIdTest(){
        long replyId = 2;
        long blogId = 2;

        replyService.deleteByReplyId(replyId);

        assertNull(replyService.findByReplyId(replyId));
        assertEquals(2, replyService.findAllByBlogId(blogId).size());
    }

    @Test
    @Transactional
    public void saveTest(){
        long blogId = 3;
        String replyWriter = "추가작성자";
        String replyContent = "추가 내용";

        ReplyCreateRequestDTO replyInsertDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();

        replyService.save(replyInsertDTO);

        List<ReplyResponseDTO> replyFindByIdDTOList = replyService.findAllByBlogId(blogId);
        assertEquals(2, replyFindByIdDTOList.size());
        assertEquals(replyWriter, replyFindByIdDTOList.get(replyFindByIdDTOList.size()-1).getReplyWriter());
    }

    @Test
    @Transactional
    public void updateTest(){
        long replyId = 2;
        String replyContent = "내용수정";

        ReplyUpdateRequestDTO replyUpdateDTO = ReplyUpdateRequestDTO.builder()
                .replyId(replyId)
                .replyContent(replyContent)
                .build();

        replyService.update(replyUpdateDTO);
    }
}
