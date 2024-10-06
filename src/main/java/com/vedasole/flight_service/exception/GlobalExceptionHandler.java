package com.vedasole.flight_service.exception;

import com.vedasole.flight_service.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(ResourceNotFoundException ex) {

        log.error("{}: {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);

        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(RuntimeException ex) {
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        log.error("{}: {}", ex.getClass().getSimpleName(), message, ex);
        ApiResponse apiResponse = new ApiResponse(ex.getMessage(),false);

        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
