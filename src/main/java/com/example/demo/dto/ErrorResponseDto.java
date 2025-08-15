package com.example.demo.dto;

public class ErrorResponseDto {
    String description;

    public ErrorResponseDto(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
