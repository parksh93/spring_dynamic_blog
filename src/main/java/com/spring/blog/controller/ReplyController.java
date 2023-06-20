package com.spring.blog.controller;

import com.spring.blog.dto.ReplyFindByIdDTO;
import com.spring.blog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reply")
public class ReplyController {
    ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService){
        this.replyService = replyService;
    }

    // 어떤 자원에 접근할것인지만 uri에 명시
    @RequestMapping(value = "/{blogId}/all", method = RequestMethod.GET)
    // rest서버는 응답시 응답코드와 응답객체를 넘기기 때문에 ResponseEntity를 리턴
    public ResponseEntity<List<ReplyFindByIdDTO>> findAllReplys(@PathVariable long blogId){
        List<ReplyFindByIdDTO> replyList = replyService.findAllByBlogId(blogId);

        return ResponseEntity.ok().body(replyList);
    }
}
