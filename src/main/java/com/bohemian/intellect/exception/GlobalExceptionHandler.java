//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.bohemian.intellect.exception;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public GlobalExceptionHandler() {
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
