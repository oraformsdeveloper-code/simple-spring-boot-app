package com.example.demo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PersonDto {
    private Long id;
    @NotNull
    private String name;
    @Email
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
