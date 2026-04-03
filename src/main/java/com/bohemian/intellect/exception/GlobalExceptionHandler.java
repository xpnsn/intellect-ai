//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomErrorResponse> handleHandlerMethodValidation(
            HandlerMethodValidationException ex) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getAllErrors().forEach(error -> {
            errors.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(
                new CustomErrorResponse(
                        "Validation Failed",
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now().toString(),
                        errors
                )
        );
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(violation -> errors.put(violation.getPropertyPath().toString(), violation.getMessage()));
        return new ResponseEntity<>(
            new CustomErrorResponse("Validation Failed", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), errors),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleHandlerMethodValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        return new ResponseEntity<>(
            new CustomErrorResponse("Validation Error", HttpStatus.BAD_REQUEST.value(), LocalDateTime.now().toString(), errors),
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({
            UnauthorizedAccessException.class,
            UserNotFoundException.class,
            ResourceNotFoundException.class
    })
    public ResponseEntity<CustomErrorResponse> handleException(RuntimeException ex) {
        HttpStatus status;
        String title;

        if (ex instanceof UserNotFoundException) {
            status = HttpStatus.UNAUTHORIZED;
            title = "User Not Found";
        } else if (ex instanceof UnauthorizedAccessException) {
            status = HttpStatus.UNAUTHORIZED;
            title = "Unauthorized Access";
        } else if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.BAD_REQUEST;
            title = "Invalid ID";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            title = "An Error Occurred";
        }

        CustomErrorResponse response = new CustomErrorResponse(
                title,
                status.value(),
                LocalDateTime.now().toString(),
                Map.of("error", ex.getMessage())
        );

        return new ResponseEntity<>(response, status);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> handleException(Exception ex) {
        return new ResponseEntity<>(new CustomErrorResponse("Server Side Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now().toString(), Map.of("errors", ex.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
