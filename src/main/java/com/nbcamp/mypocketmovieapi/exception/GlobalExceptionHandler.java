package com.nbcamp.mypocketmovieapi.exception;

import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<CommonResponse<Void>> handleDuplicateEmail(DuplicateEmailException e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(commonResponse);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleMemberNotFound(MemberNotFoundException e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

}
