package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_METHOD) //@AfterAll시 DROP TABLE할때 필요한 어노테이션
public class BlogRepositoryTest {
    @Autowired
    BlogRepository blogRepository;

    @BeforeEach //각 테스트 전에 공통적으로 실행할 코드를 저장해두는곳
    public void setBlogTable(){
        blogRepository.createBlogTable();
        blogRepository.insertTestData();
    }

    @Test
    public void findAllTest(){
        int blogRow = 1;

        List<Blog> blogList = blogRepository.findAll();

        assertEquals(3, blogList.size());
        assertEquals(2,blogList.get(blogRow).getBlogId());
    }

    @Test
    @DisplayName("2번 글의 작성자, 제목, id 검사")
    public void findByIdTest(){
        int blogId = 2;

        Blog blog = blogRepository.findById(blogId);

        assertEquals("2번유저",blog.getWriter());
        assertEquals("2번제목",blog.getBlogTitle());
        assertEquals(2,blog.getBlogId());
    }

    @Test
    public void saveTest(){
        //fixture setter로 저장
        String writer = "추가유저";
        String blogTitle = "추가글제목";
        String blogContent = "추가글내용";

//        Blog blog = new Blog();
//        blog.setWriter(writer);
//        blog.setBlogTitle(blogTitle);
//        blog.setBlogContent(blogContent);
        //빌더패턴
        Blog blog = Blog.builder()
                .writer(writer)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();

        blogRepository.save(blog);
        List<Blog> blogList = blogRepository.findAll();

        int blogRow = 3;

        assertEquals(4, blogList.size());
        assertEquals(writer,blogList.get(blogRow).getWriter());
    }

    @Test
    @DisplayName("2번 글을 삭제 후 남은 데이터가 2개인지 검사")
    public void deleteByIdTest(){
        long blogId = 2;

        blogRepository.deleteById(blogId);
//        List<Blog> blogList = blogRepository.findAll();
//        Blog blog = blogRepository.findById(blogId);

        assertEquals(2, blogRepository.findAll().size());
        assertNull(blogRepository.findById(blogId));
    }

    @Test
    public  void updateTest(){
        int blogId = 3;
        String blogTitle = "바꾼제목";
        String blogContent = "바꾼내용";

        Blog blog = Blog.builder()
                .blogId(blogId)
                .blogTitle(blogTitle)
                .blogContent(blogContent)
                .build();
        // 빌더패턴 이외의 방법 : 3번 데이터를 가져와서 변경되는 부분만 setter로 수정

        blogRepository.update(blog);

        assertEquals(blogTitle, blogRepository.findById(blog.getBlogId()).getBlogTitle());
        assertEquals(blogContent, blogRepository.findById(blog.getBlogId()).getBlogContent());
    }

    @AfterEach  //각 단위 테스트 끝난 후에 실행할 구문 작성
    public void dropBlogTable(){
        blogRepository.dropBlogTable();
    }
}
