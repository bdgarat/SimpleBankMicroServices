package com.bdgarat.sbmsaccountservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchResourceFoundException ex,
                                                              HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("error", HttpStatus.NOT_FOUND.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadResourceRequestException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(BadResourceRequestException ex,
                                                                HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExternalServiceUnreacheable.class)
    public ResponseEntity<Map<String, Object>> handleServiceUnavailable(ExternalServiceUnreacheable ex,
                                                                HttpServletRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        body.put("error", HttpStatus.SERVICE_UNAVAILABLE.toString());
        body.put("message", ex.getMessage());
        body.put("path", request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.SERVICE_UNAVAILABLE);
    }
}