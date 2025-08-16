package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponseDto {
    private String description;
    private String errorMessage;
}
