package com.spring.blog.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper //빈 컨테이너에 Mybatis 관리 파일로서 적재
public interface ConnectionTestRepository {
    String getNow();
}
