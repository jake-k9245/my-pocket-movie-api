package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.dto.reviewLike.FindAllReviewLikeResponseDto;
import com.nbcamp.mypocketmovieapi.service.ReviewLikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewLikeController {
    private final ReviewLikeService reviewLikeService;

    @PostMapping("/reviews/{reviewId}/likes")
    public ResponseEntity<String> toggleReviewLike(@PathVariable Long reviewId) {

        Long memberId = 1L;
        reviewLikeService.toggleReviewLike(memberId, reviewId);
        return ResponseEntity.status(HttpStatus.CREATED).body("해당 리뷰에 좋아요를 눌렀습니다.");
    }

    @GetMapping("/liked-reviews")
    public ResponseEntity<List<FindAllReviewLikeResponseDto>> findAllReviewLike() {
        Long memberId = 1L;
        return ResponseEntity.ok(reviewLikeService.findAllReviewLike(memberId));
    }
}


