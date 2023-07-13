package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogJPARepository;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyJPARepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service //빈 컨테이너에 적재
public class BlogServiceImp implements BlogService{
    BlogRepository blogRepository;
    ReplyRepository replyRepository;

    BlogJPARepository blogJPARepository;
    ReplyJPARepository replyJPARepository;

    @Autowired //생성자 주입이 setter주입보다 속도가 빠르다
    public BlogServiceImp(BlogRepository blogRepository, ReplyRepository replyRepository, BlogJPARepository blogJPARepository, ReplyJPARepository replyJPARepository) {
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
        this.blogJPARepository = blogJPARepository;
        this.replyJPARepository = replyJPARepository;
    }

    @Override
    public Page<Blog> findAll(Long pageNumber) {   // 페이지 정보를 함께 포함하고 있는 리스트인 Page를 리턴
//        return  blogRepository.findAll(); //Mybatis
//        return blogJPARepository.findAll(); //JPA
        if (pageNumber == null || pageNumber <= 0){
            pageNumber = 1L;
        }

        long totalPageCount = Math.round(blogJPARepository.count() / 10.0);

        pageNumber = pageNumber > totalPageCount ? totalPageCount : pageNumber;

        // 페이징 처리에 관련된 정보를 먼저 객체로 생성
        Pageable pageable = PageRequest.of((int)(pageNumber-1), 10, Sort.by("blogId").descending());

        Page<Blog> blogPage = blogJPARepository.findAll(pageable);

//        if (blogPage.getTotalPages()-1 < (int)(pageNumber-1)){
//            return blogJPARepository.findAll(PageRequest.of(blogPage.getTotalPages()-1,10));
//        }
        // 생성된 페이지 정보를 파라미터로 제공해 findAll()을 호출
        return blogPage;
    }

    @Override
    public Blog findById(long blogId) {
//        return blogRepository.findById(blogId);
        return blogJPARepository.findById(blogId).get(); // JPA의 findById는 Optional(참조형 변수에 대해서 null값을 검사및 처리를 쉽게 할수 있는 제너릭) 을 리턴하기 때문에
                                                        // 일반 객체로 만들기 위해서는 .get()을 붙여줘야한다.
                                                        // JPA에서는 Optional 사용 권장
                                                                                        
    }

    @Override
    @Transactional //둘다 실행되던디 둘 다 실행 안되던지 해야하기 떄문에
    public void deleteById(long blogId) {
//        replyRepository.deleteAllByBlogId(blogId);
//        blogRepository.deleteById(blogId);
        replyJPARepository.deleteByBlogId(blogId);
        blogJPARepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
//        blogRepository.save(blog);
        blogJPARepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        // JPA의 수정은, findById()를 이용해 얻어온 엔터티 클래스의 객체 내부 내용물을 수정한 다음
        // 해당 요소를 save()해서 이뤄진다.

//        blogRepository.update(blog);
        Blog findBlog = blogJPARepository.findById(blog.getBlogId()).get(); // 준영속 상태

        findBlog.setBlogTitle(blog.getBlogTitle());
        findBlog.setBlogContent(blog.getBlogContent());

        blogJPARepository.save(findBlog);
    }

}