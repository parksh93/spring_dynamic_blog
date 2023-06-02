package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import com.spring.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service //빈 컨테이너에 적재
public class BlogServiceImp implements BlogService{
    BlogRepository blogRepository;

    @Autowired //생성자 주입이 setter주입보다 속도가 빠르다
    public BlogServiceImp(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
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
    public void deleteById(long blogId) {
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
