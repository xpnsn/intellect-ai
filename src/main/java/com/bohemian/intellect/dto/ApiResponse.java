package com.bohemian.intellect.dto;

import org.springframework.http.HttpStatus;

public record ApiResponse(String message, int status, String httpStatus) {

    public static ApiResponse of(String message, HttpStatus status) {
        return new ApiResponse(message, status.value(), status.name());
    }
}

