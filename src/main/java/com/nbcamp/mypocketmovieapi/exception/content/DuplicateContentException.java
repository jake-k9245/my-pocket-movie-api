package com.nbcamp.mypocketmovieapi.exception.content;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class DuplicateContentException extends RuntimeException {
    private final CommonCode commonCode;

    public DuplicateContentException(CommonCode code) { // ex) CommonCode code = CommonCode.FAIL_DUPLICATE_EMAIL
        this.commonCode = code;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
