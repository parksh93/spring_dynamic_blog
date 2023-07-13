package com.spring.blog.service;

import com.spring.blog.entity.Blog;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
    Page<Blog> findAll(Long pageNumber);

    Blog findById(long blogId);

    void deleteById(long blogId);

    void save(Blog blog);

    void update(Blog blog);
}
