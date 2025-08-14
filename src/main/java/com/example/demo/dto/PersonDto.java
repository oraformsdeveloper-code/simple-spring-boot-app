package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonDto {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
