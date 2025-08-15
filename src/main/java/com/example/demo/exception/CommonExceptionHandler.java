package com.example.demo.exception;

import com.example.demo.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleNotFoundException(ResourceNotFoundException exception) {
        // TODO: заменить на ErrorResponseDto
        //return Map.of("description", exception.getMessage());
        return new ErrorResponseDto(exception.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponseDto(exception.getMessage()));

//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new ErrorMessage(exception.getMessage()));
    }
}
