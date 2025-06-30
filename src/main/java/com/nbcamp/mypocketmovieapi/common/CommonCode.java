package com.nbcamp.mypocketmovieapi.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonCode { // 우리는 크기가 안크니까 에러와 성공을 하나의 이넘으로 관리
    // Member
        // SUCCESS
    SUCCESS_SIGNUP(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), "회원가입에 성공하셨습니다."),
    SUCCESS_SIGNIN(HttpStatus.OK.value(), HttpStatus.OK.name(), "로그인에 성공하셨습니다."),
        //FAIL
    FAIL_DUPLICATE_EMAIL(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), "이미 사용 중인 이메일입니다."),
    FAIL_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "회원을 찾을 수 없습니다."),
    FAIL_INVALID_EMAIL(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "이메일이 올바르지 않습니다."),
    FAIL_SIGNIN_INVALID_PASSWORD(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), "비밀번호가 올바르지 않습니다."),
    FAIL_UPDATE_MISMATCH_PASSWORD(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "기존 비밀번호가 올바르지 않습니다."),

    // Content
        // FAIL
    FAIL_DUPLICATE_CONTENT(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), "이미 등록된 콘텐츠입니다."),
    FAIL_CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "해당 콘텐츠를 찾을 수 없습니다."),
    FAIL_UNAUTHORIZED_CONTENT_DELETION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "해당 콘텐츠를 등록 회원만 삭제 가능합니다."),

    // Review
        //FAIL
    FAIL_REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "리뷰를 찾을 수 없습니다."),
    FAIL_UNAUTHORIZED_REVIEW_MODIFICATION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "리뷰는 작성자만 수정할 수 있습니다."),
    FAIL_UNAUTHORIZED_REVIEW_DELETION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "리뷰는 작성자만 삭제할 수 있습니다."),

    // Review Like
    SUCCESS_REVIEW_LIKED(HttpStatus.CREATED.value(), HttpStatus.CREATED.name(), "해당 리뷰에 좋아요를 눌렀습니다."),
    SUCCESS_REVIEW_UNLIKED(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.name(), "해당 리뷰에 좋아요를 취소했습니다."),

    // Comment
    FAIL_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "댓글을 찾을 수 없습니다."),
    FAIL_UNAUTHORIZED_COMMENT_MODIFICATION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "댓글은 작성자만 수정할 수 있습니다."),
    FAIL_UNAUTHORIZED_COMMENT_DELETION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "댓글은 작성자만 삭제할 수 있습니다."),

    // Wishlist
    FAIL_DUPLICATE_WISHLIST(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), "이미 등록한 콘텐츠를 위시리스트에 다시 추가할 수 없습니다."),
    FAIL_WISHLIST_NOT_FOUND(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), "위시리스트를 찾을 수 없습니다."),
    FAIL_UNAUTHORIZED_WISHLIST_DELETION(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), "위시리스트는 작성자만 삭제할 수 있습니다."),


    // 귀찮은 사람을 위한 만능 이넘
    SUCCESS(HttpStatus.OK.value(), HttpStatus.OK.name(), "요청을 성공했습니다."), //이넘 하나 하나가 다 클래스임
    FAIL_CLIENT(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), "잘못된 요청입니다. 요청 정보를 확인해주세요."),
    FAIL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), "서버에서 오류가 발생했습니다. 관리자에게 문의해주세요.");

    private final int code;
    private final String status;
    private final String message;

    CommonCode(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
