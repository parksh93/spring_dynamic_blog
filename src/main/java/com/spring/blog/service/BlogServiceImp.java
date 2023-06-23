package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import com.spring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service //빈 컨테이너에 적재
public class BlogServiceImp implements BlogService{
    BlogRepository blogRepository;
    ReplyRepository replyRepository;

    @Autowired //생성자 주입이 setter주입보다 속도가 빠르다
    public BlogServiceImp(BlogRepository blogRepository, ReplyRepository replyRepository){
        this.blogRepository = blogRepository;
        this.replyRepository = replyRepository;
    }

    @Override
    public List<Blog> findAll() {
        return  blogRepository.findAll();
    }

    @Override
    public Blog findById(long blogId) {
        return blogRepository.findById(blogId);
    }

    @Override
    @Transactional //둘다 실행되던디 둘 다 실행 안되던지 해야하기 떄문에
    public void deleteById(long blogId) {
        replyRepository.deleteAllByBlogId(blogId);
        blogRepository.deleteById(blogId);
    }

    @Override
    public void save(Blog blog) {
        blogRepository.save(blog);
    }

    @Override
    public void update(Blog blog) {
        blogRepository.update(blog);
    }

}