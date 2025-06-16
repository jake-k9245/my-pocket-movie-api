package com.nbcamp.mypocketmovieapi.exception.content;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class UnAuthorizedContentException extends RuntimeException {
    private final CommonCode commonCode;

    public UnAuthorizedContentException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
