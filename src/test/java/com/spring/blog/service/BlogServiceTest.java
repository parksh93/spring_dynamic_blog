package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BlogServiceTest {
    @Autowired
    BlogService blogService;

    @Test
    @Transactional  // 이 테스트의 결과가 DB에 영향을 주지 않는다(커밋하지 않고 롤백한다)
                    // 멀티 쓰레드 환경에서 @Transational을 붙여줘야 트랜젝션의 acid중 isolation(독립성)을 보장받기 때문에
                    // 쓰레드 세이프티를 보장받을 수 있다.
                    // JPA의 경우 무조건적으로 사용하면 안된다.
    public void findAllTest(){
        List<Blog> blogList = blogService.findAll();

//        assertEquals(3,blogList.size()); //아래 코드와 동일
        assertThat(blogList.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void findByIdTest(){
        long blogId = 2;

        Blog blog = blogService.findById(blogId);

        assertEquals("2번유저",blog.getWriter());
        assertEquals("2번제목",blog.getBlogTitle());
        assertEquals("2번본문",blog.getBlogContent());
    }

    @Test
    @Transactional
    //@Commit  //트랜젝션이 적용된 테스트의 결과를 커밋해서 디비에 반영
    public void deleteByIdTest(){
        long blogId = 2;

        blogService.deleteById(blogId);

        assertEquals(2,blogService.findAll().size());
        assertNull(blogService.findById(blogId));
    }

    @Test
    @Transactional // rollback을 해도 primary key(여기서는 blog_id)는 초기화되지 않는다
                    // 따라서 한번 사용한 blog_id는 다음 테스트에서 실패가 뜬다
    public void saveTest(){
        Blog blog = Blog.builder()
                .writer("추가유저")
                .blogTitle("추가제목")
                .blogContent("추가본문")
                .build();

        blogService.save(blog);
        assertEquals(4, blogService.findAll().size());
        assertEquals("추가유저", blogService.findAll().get(3).getWriter());
    }

    @Test
    @Transactional
    public void update(){
        int blogId = 3;
        String blogTitle = "수정제목";
        String blogContent = "수정본문";

        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        blogService.update(blog);

        assertEquals(blogTitle,blogService.findById(blogId).getBlogTitle());
        assertEquals(blogContent,blogService.findById(blogId).getBlogContent());
    }

    //blog와 함께 reply가 삭제되는 케이스는 따로 다시 테스트코드를 하나 더 작성해주는게 좋다.
}
