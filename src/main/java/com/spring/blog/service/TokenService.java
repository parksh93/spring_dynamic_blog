package com.spring.blog.service;

import com.spring.blog.config.jwt.TokenProvider;
import com.spring.blog.entity.User;
import com.spring.blog.repository.RefreshTokenRepository;
import com.spring.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String createNewAccessToken(String refreshToken){
        //리프레시 토큰도 JWT 스펙으로 만들어지기 때문에, TokenPorvider에 의한 유효성 검증이 가능
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpectied Token!!");
        } // 유효하지 않은 토큰이면 예외 발생

        // 유효한 토큰이면 어떤 유저의 토큰인지 먼저 확인
        Long userId = refreshTokenRepository.findByRefreshToken(refreshToken).getUserId();

        User user = userRepository.findById(userId).get();

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
