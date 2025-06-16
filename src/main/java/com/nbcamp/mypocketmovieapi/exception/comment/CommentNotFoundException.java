package com.nbcamp.mypocketmovieapi.exception.comment;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class CommentNotFoundException extends RuntimeException {
    private final CommonCode commonCode;

    public CommentNotFoundException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
