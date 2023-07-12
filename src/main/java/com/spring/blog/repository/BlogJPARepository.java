package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogJPARepository extends JpaRepository<Blog, Long> {

}
