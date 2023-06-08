package com.spring.blog.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BmiDTO {
    private double height;
    private double weight;
}
