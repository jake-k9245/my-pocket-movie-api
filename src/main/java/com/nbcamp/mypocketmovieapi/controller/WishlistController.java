package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.WishlistResponseDto;
import com.nbcamp.mypocketmovieapi.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    // POST http://localhost:8080/api/contents/1/wishlists
    @PostMapping("/api/contents/{contentId}/wishlists")
    public ResponseEntity<Void> saveWishlist(@PathVariable Long contentId) {
        Long memberId = 1L;
        wishlistService.saveWishlist(memberId, contentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/wishlists")
    public ResponseEntity<List<WishlistResponseDto>> getWishlists() {
        Long memberId = 1L;
        return ResponseEntity.ok(wishlistService.getWishlists(memberId));
    }

    @DeleteMapping("/api/wishlists/{wishlistId}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Long wishlistId) {
        Long memberId = 1L;
        wishlistService.deleteWishlist(memberId, wishlistId);
        return ResponseEntity.noContent().build();
    }

}
