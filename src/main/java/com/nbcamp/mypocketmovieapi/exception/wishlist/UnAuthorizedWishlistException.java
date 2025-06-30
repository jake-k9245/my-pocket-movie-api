package com.nbcamp.mypocketmovieapi.exception.wishlist;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class UnAuthorizedWishlistException extends RuntimeException {
    private final CommonCode commonCode;

    public UnAuthorizedWishlistException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }
}
