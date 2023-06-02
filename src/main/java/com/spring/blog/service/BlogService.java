package com.spring.blog.service;

import com.spring.blog.entity.Blog;

import java.util.List;

public interface BlogService {
    List<Blog> findAll();

    Blog findById(long blogId);

    void deleteById(long blogId);

    void save(Blog blog);

    void update(Blog blog);
}
