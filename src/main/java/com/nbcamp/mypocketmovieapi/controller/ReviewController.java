package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.dto.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewUpdateRequestDto;
import com.nbcamp.mypocketmovieapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewResponseDto> save(@PathVariable Long contentId, @RequestBody ReviewCreateRequestDto requestDto) {
        Long memberId = 1L;
        ReviewResponseDto reviewResponseDto = reviewService.save(contentId, memberId, requestDto);
        return ResponseEntity.ok(reviewResponseDto);
    }

    // Review 전체 조회 API
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviews();
        return ResponseEntity.ok(reviewResponseDtoList);
    }

    // 특정 콘텐츠의 Review 전체 조회 API = select * from reviews where content_id = ?;
    @GetMapping("/contents/{contentId}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsBy(@PathVariable Long contentId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviewsBy(contentId);
        return ResponseEntity.ok(reviewResponseDtoList);
    }

    //
    @GetMapping("/contents/{contentId}}/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> findById(@PathVariable Long id) {


    }

    // PUT http://localhost:8080/api/reviews body : { "rating": 1, "text": "리뷰 수정" }
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> updateReviews(@PathVariable Long reviewId, @RequestBody ReviewUpdateRequestDto requestDto) {
        Long memberId = 1L;
        reviewService.updateReviews(memberId, reviewId, requestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        Long memberId = 1L;
        reviewService.deleteReview(memberId, reviewId);
        return ResponseEntity.noContent().build();
    }

}
