package com.nbcamp.mypocketmovieapi.exception;

import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.member.SignInInvalidPassword;
import com.nbcamp.mypocketmovieapi.exception.member.UpdateMismatchPassword;
import com.nbcamp.mypocketmovieapi.exception.review.ReviewNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.review.UnAuthorizedReviewException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(UnAuthorizedReviewException.class)
    public ResponseEntity<CommonResponse<Void>> handleReviewUnAuthorized(UnAuthorizedReviewException e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<CommonResponse<Void>> handleReviewNotFound(ReviewNotFoundException e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonResponse);
    }

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

    @ExceptionHandler(SignInInvalidPassword.class)
    public ResponseEntity<CommonResponse<Void>> handleSignInInvalidPassword(SignInInvalidPassword e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(commonResponse);
    }

    @ExceptionHandler(UpdateMismatchPassword.class)
    public ResponseEntity<CommonResponse<Void>> handleUpdateMismatchPassword(UpdateMismatchPassword e) {

        CommonResponse<Void> commonResponse = CommonResponse.fail(e.getCommonCode());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commonResponse);
    }

}
