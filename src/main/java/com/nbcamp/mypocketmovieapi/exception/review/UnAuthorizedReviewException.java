package com.nbcamp.mypocketmovieapi.exception.review;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class UnAuthorizedReviewException extends RuntimeException {
    private final CommonCode commonCode;

    public UnAuthorizedReviewException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
