package com.nbcamp.mypocketmovieapi.exception.comment;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class UnAuthorizedCommentException extends RuntimeException {
    private final CommonCode commonCode;

    public UnAuthorizedCommentException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
