package com.nbcamp.mypocketmovieapi.exception.member;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class MemberNotFoundException extends RuntimeException {
    private final CommonCode code;

    public MemberNotFoundException(CommonCode code) {
        this.code = code;
    }

    public CommonCode getCommonCode() {
        return this.code;
    }
}
