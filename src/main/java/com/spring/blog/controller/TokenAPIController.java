package com.spring.blog.controller;

import com.spring.blog.dto.AccessTokenResponseDTO;
import com.spring.blog.dto.RefreshTokenRequestDTO;
import com.spring.blog.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenAPIController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<AccessTokenResponseDTO> createNewAccessToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){
        String newAccessToken = tokenService.createNewAccessToken(refreshTokenRequestDTO.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AccessTokenResponseDTO(newAccessToken));
    }
}
