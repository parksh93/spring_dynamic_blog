package com.spring.blog.repository;

import com.spring.blog.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByUserId(Long userId);

    RefreshToken findByRefreshToken(String refreshToken);
}
