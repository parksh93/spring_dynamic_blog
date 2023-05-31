package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogRepository {
    // 사전에 정의해야 하는 메스드
    //테이블 생성
    void createBlogTable();

    //더미테이터 입력
    void insertTestData();

    //테이블 삭제
    void dropBlogTable();   //단위테스트 종료 후 DB초기화를 위해 테이블 삭제

    // 전체 데이터 조회
    List<Blog> findAll();
}
