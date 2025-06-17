package com.nbcamp.mypocketmovieapi.exception.wishlist;

import com.nbcamp.mypocketmovieapi.common.CommonCode;

public class DuplicateWishlistException extends RuntimeException {
    private final CommonCode commonCode;

    public DuplicateWishlistException(CommonCode commonCode) {
        this.commonCode = commonCode;
    }

    public CommonCode getCommonCode() {
        return this.commonCode;
    }
}
