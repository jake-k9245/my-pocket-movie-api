package com.nbcamp.mypocketmovieapi.exception;

import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<?> handleDuplicateEmail(DuplicateEmailException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "code", 409,
                "message", e.getMessage()
        ));
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<?> handleMemberNotFound(MemberNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "code", 404,
                "status", "NOT_FOUND",
                "data", e.getMessage()
        ));
    }

}
