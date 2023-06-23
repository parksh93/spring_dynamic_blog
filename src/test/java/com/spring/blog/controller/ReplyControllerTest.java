package com.spring.blog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.spring.blog.dto.ReplyCreateRequestDTO;

import com.spring.blog.dto.ReplyUpdateRequestDTO;
import com.spring.blog.dto.ReplyCreateRequestDTO;

import com.spring.blog.repository.ReplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc //MVC테스트는 브라우저를 켜야 원래 테스트가 가능하므로 브라우저를 대체할(브라우저 역할을 해 줄) 객체를 만들어 수행
        // MockMvc를 빈컨테이너에 적재하는 어노테이션
        //MocMvc을 사용하는 이유는 단순히 해당 메서드가 돌아가는지가 아닌 해당 주소로 접속했을때 접속이 이루어지는지와 값이 잘 나오는지 검사하기 위해서이다
class ReplyControllerTest {

    @Autowired
    ReplyRepository replyRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;  //데이터 직렬화에 사용하는 객체

    // 컨트롤러를 테스트 해야하는데 컨트롤러는 서버에 url만 입력하면 동작하므로 컨트롤러를 따로 생성하지 않아도 된다.
    @BeforeEach
    public  void setMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Transactional
    void findAllRepliesTest() throws Exception { //MockMvc의 예외를 던져줌 (perfom떄문에 사용)
        //given
        String replyWriter = "박자바";
        long replyId = 1;
        String url = "/reply/2/all";

        //when
        //json데이터 리턴
        //ResultAction형 자료로 json 저장 (js의 fetch(url, {method:'get'}).then(res => res.json())와 같다)
        //get() :메서드 경우 alt + enter를 눌러 mockmvc관련 요서(MockMvcRequestBuilders) import
        final ResultActions result = mockMvc.perform(get(url) //get방식으로 요청
                .accept(MediaType.APPLICATION_JSON)); //리턴 자료향이 json임을 명시

        //then
        //리턴 받은 json 목록의 0번재 요소 검사
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyId").value(replyId));
    }

    @Test
    @Transactional
    public void findReplyTest() throws Exception{
        long replyId = 2;
        String url = "/reply/"+replyId;
        String replyWriter ="박w";

        final ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.replyWriter").value(replyWriter))
                .andExpect(jsonPath("$.replyId").value(replyId));
    }

    @Test
    @Transactional
    public void inertReplyTest() throws Exception{
        long blogId = 1;
        String replyWriter = "또사라짐";
        String replyContent = "가기싫다";
        String url = "/reply";
        String url2 = "/reply/1/all";

        ReplyCreateRequestDTO replyInsertDTO = ReplyCreateRequestDTO.builder()
                .blogId(blogId)
                .replyWriter(replyWriter)
                .replyContent(replyContent)
                .build();
        //json으로 데이터 직렬화
        final String requestBody = objectMapper.writeValueAsString(replyInsertDTO);

        mockMvc.perform(post(url)   //post 요청
                .contentType(MediaType.APPLICATION_JSON) //전달 자료 타입명시
                .content(requestBody));   //전달자료

        final ResultActions result = mockMvc.perform(get(url2)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].replyWriter").value(replyWriter))
                .andExpect(jsonPath("$[0].replyContent").value(replyContent));
    }

    @Test
    @Transactional
    public void deleteReplyTest() throws Exception{
        long replyId = 3;
        long blogId = 2;
        String url = "/reply/"+ replyId;

        mockMvc.perform(delete(url) //.accept는 리턴 데잍가 있는 경우에 해당 데이터를 어떤 방식으로 받을지 기술
                .accept(MediaType.TEXT_PLAIN));

        assertEquals(2,replyRepository.findAllByBlogId(blogId).size());
        assertNull(replyRepository.findByReplyId(replyId));
    }
}