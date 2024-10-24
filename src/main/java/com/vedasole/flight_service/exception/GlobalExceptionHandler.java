package com.vedasole.flight_service.exception;

import com.vedasole.flight_service.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {

        log.error("ResourceNotFoundException: {}", ex.getMessage(), ex);
        ApiResponse apiResponse = new ApiResponse(ex.getClass().getSimpleName(),ex.getMessage(),false);

        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        HashMap<String, String> errorList = new HashMap<>();

        ex.getAllErrors().forEach(e -> {
            String objectName = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            errorList.put(objectName, message);
        });

        ApiResponse apiResponse = new ApiResponse("MethodArgumentNotValidException", errorList,false);

        log.error("MethodArgumentNotValidException: {}", errorList, ex);

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAllExceptions(RuntimeException ex) {
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();

        log.error("{}: {}", ex.getClass().getSimpleName(), message, ex);
        ApiResponse apiResponse = new ApiResponse(ex.getClass().getSimpleName(),ex.getMessage(),false);

        return new ResponseEntity<>(
                apiResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}