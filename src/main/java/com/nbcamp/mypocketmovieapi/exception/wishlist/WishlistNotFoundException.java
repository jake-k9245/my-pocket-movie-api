package com.nbcamp.mypocketmovieapi.exception.wishlist;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class WishlistNotFoundException extends RuntimeException {
    private final CommonCode commonCode;

    public WishlistNotFoundException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }
}
