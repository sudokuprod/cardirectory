package com.sudokuprod.cardirectory.controller;

import com.sudokuprod.cardirectory.pojo.ExceptionPojo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultExceptionHandler(Exception e) {
        return new ResponseEntity<>(
                new ExceptionPojo(e, HttpStatus.INTERNAL_SERVER_ERROR),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
