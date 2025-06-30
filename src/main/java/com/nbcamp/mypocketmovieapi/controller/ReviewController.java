package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.review.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.ReviewUpdateRequestDto;
import com.nbcamp.mypocketmovieapi.security.UserDetailsImpl;
import com.nbcamp.mypocketmovieapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // POST http://localhost:8080/api/contents/1/reviews body : { "rating": 1, "text": "리뷰 Good" }
    // Review 등록 API
    @PostMapping("/contents/{contentId}/reviews")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> save(
            @PathVariable Long contentId,
            @RequestBody ReviewCreateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ReviewResponseDto reviewResponseDto = reviewService.save(contentId, userDetails.getMember().getId(), requestDto);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, reviewResponseDto));
    }

    // Review 전체 조회 API
    @GetMapping("/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getReviews() {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviews();
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, reviewResponseDtoList));
    }

    // 특정 콘텐츠의 Review 전체 조회 API = select * from reviews where content_id = ?;
    @GetMapping("/contents/{contentId}/reviews")
    public ResponseEntity<CommonResponse<List<ReviewResponseDto>>> getReviewsBy(@PathVariable Long contentId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewsBy(contentId);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, reviewResponseDtoList));
    }

    //Review 단건 조회 API
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<ReviewResponseDto>> findById(@PathVariable Long reviewId) {
        ReviewResponseDto reviewResponseDto = reviewService.findById(reviewId);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, reviewResponseDto));
    }

    // PUT http://localhost:8080/api/reviews body : { "rating": 1, "text": "리뷰 수정" }
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> updateReviews(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
        ) {
        reviewService.updateReviews(userDetails.getMember().getId(), reviewId, requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<CommonResponse<Void>> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reviewService.deleteReview(userDetails.getMember().getId(), reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

}
