package com.nbcamp.mypocketmovieapi.exception.member;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class UpdateMismatchPassword extends RuntimeException {
    private CommonCode commonCode;

    public UpdateMismatchPassword(CommonCode code){
        commonCode = code;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

}
