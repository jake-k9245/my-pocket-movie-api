package com.nbcamp.mypocketmovieapi.exception.review;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class ReviewNotFoundException extends RuntimeException {
    private final CommonCode commonCode;

    public ReviewNotFoundException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
