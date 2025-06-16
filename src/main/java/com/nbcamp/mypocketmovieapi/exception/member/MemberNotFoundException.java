package com.nbcamp.mypocketmovieapi.exception.member;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class MemberNotFoundException extends RuntimeException {
    private final CommonCode commonCode;

    public MemberNotFoundException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
