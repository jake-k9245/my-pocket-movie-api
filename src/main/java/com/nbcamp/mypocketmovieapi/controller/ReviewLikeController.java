package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.reviewLike.FindAllReviewLikeResponseDto;
import com.nbcamp.mypocketmovieapi.service.ReviewLikeService;
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
    public ResponseEntity<CommonResponse<Void>> toggleReviewLike(@PathVariable Long reviewId, @SigninMember Long memberId) {
        CommonCode code = reviewLikeService.toggleReviewLike(memberId, reviewId);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.success(code));
    }

    @GetMapping("/liked-reviews")
    public ResponseEntity<CommonResponse<List<FindAllReviewLikeResponseDto>>> findAllReviewLike(@SigninMember Long memberId) {
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, reviewLikeService.findAllReviewLike(memberId)));
    }
}


