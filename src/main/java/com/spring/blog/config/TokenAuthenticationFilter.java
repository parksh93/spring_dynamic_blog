package com.spring.blog.config;

import com.spring.blog.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    // 토큰은 RequestHeader에 포함되어 들어오므로, 해당 요소를 얻어낼 수 있도록 명치 상수화
    private final static String HEADER_AUTHORIZATION = "Authorization";
    // 토큰 기반 인증시 실제 토큰값 앞에 접두어로 붙는 Bearer 제거를 위한 상수(맨 뒤에 띄어쓰기 포함)
    private final static  String TOKEN_PREFIX = "Bearer ";

    // 요청이 들어올때마다 실행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 토큰은 요청헤더를 이용해서 제출되기 때문에, request에서 header를 얻어와 Authorization으로 시작하는 값을 얻어온다.
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 얻어온 토큰에서 Bearer 접두사 삭제하고 토큰값만 얻어오기
        String token= getAccessToken(authorizationHeader);

        if(tokenProvider.validToken(token)){    // 얻어온 토큰이 유효 토큰이라면
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 완료하기
        }

        filterChain.doFilter(request, response);
    }

    // Bearer 접두사 제거
    public String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) { // 토큰이 발급된 상태이며 Bearer로 시작한다면
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
