package com.nbcamp.mypocketmovieapi.exception.member;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class DuplicateEmailException extends RuntimeException {
    private final CommonCode commonCode;

    public DuplicateEmailException(CommonCode code) { // ex) CommonCode code = CommonCode.FAIL_DUPLICATE_EMAIL
        this.commonCode = code;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }
}
