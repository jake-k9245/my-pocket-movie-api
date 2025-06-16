package com.nbcamp.mypocketmovieapi.exception.member;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class SignInInvalidPassword extends RuntimeException {
    private CommonCode commonCode;

    public SignInInvalidPassword(CommonCode code){
        commonCode = code;
    }

    public CommonCode getCommonCode() {
        return commonCode;
    }

}
