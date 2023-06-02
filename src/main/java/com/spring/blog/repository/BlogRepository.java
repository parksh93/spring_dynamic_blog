package com.spring.blog.repository;

import com.spring.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // @Repository 어노테이션의 경우 빈컨테이너에 적재하는 용도고
        // @Mapper 어노테이션의 경우 빈 컨테이너에서 구현체로 만들어줘서 MyBatis와 연동될 수 있게 해주기 때문에
        // @Mapper를 써야한다
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

    Blog findById(long blogId);

    void save(Blog blog);

    void deleteById(long blogId);

    //JPA에서는 .save()를 동일하게 쓰지만, 현재 코드에서 메서드 오버로딩도 불가능하고
    //분리할 방법이 없으므로 메서드명을 다르게 사용한다
    void update(Blog blog);


}
