package com.spring.blog.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
public class AccessTokenResponseDTO {
    private String accessToken;
}
