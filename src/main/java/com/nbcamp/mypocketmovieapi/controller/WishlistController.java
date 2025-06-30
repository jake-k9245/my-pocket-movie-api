package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.WishlistResponseDto;
import com.nbcamp.mypocketmovieapi.dto.member.MemberProfileDto;
import com.nbcamp.mypocketmovieapi.security.UserDetailsImpl;
import com.nbcamp.mypocketmovieapi.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    // POST http://localhost:8080/api/contents/1/wishlists
    @PostMapping("/api/contents/{contentId}/wishlists")
    public ResponseEntity<CommonResponse<Void>> saveWishlist(@PathVariable Long contentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        wishlistService.saveWishlist(userDetails.getMember().getId(), contentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

    @GetMapping("/api/wishlists")
    public ResponseEntity<CommonResponse<List<WishlistResponseDto>>> getWishlists(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, wishlistService.getWishlists(userDetails.getMember().getId())));
    }

    @DeleteMapping("/api/wishlists/{wishlistId}")
    public ResponseEntity<CommonResponse<Void>> deleteWishlist(@PathVariable Long wishlistId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        wishlistService.deleteWishlist(userDetails.getMember().getId(), wishlistId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

}