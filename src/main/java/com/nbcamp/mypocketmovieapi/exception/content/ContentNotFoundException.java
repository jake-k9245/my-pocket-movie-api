package com.nbcamp.mypocketmovieapi.exception.content;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class ContentNotFoundException extends RuntimeException {
    private final CommonCode commonCode;

    public ContentNotFoundException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }

}
