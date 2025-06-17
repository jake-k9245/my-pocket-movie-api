package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.WishlistResponseDto;
import com.nbcamp.mypocketmovieapi.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    // POST http://localhost:8080/api/contents/1/wishlists
    @PostMapping("/api/contents/{contentId}/wishlists")
    public ResponseEntity<CommonResponse<Void>> saveWishlist(@PathVariable Long contentId, @SigninMember Long memberId) {
        wishlistService.saveWishlist(memberId, contentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

    @GetMapping("/api/wishlists")
    public ResponseEntity<CommonResponse<List<WishlistResponseDto>>> getWishlists(@SigninMember Long memberId) {
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, wishlistService.getWishlists(memberId)));
    }

    @DeleteMapping("/api/wishlists/{wishlistId}")
    public ResponseEntity<CommonResponse<Void>> deleteWishlist(@PathVariable Long wishlistId, @SigninMember Long memberId) {
        wishlistService.deleteWishlist(memberId, wishlistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

}