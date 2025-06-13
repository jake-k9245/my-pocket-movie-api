package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.dto.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    // Review 등록 API
    @PostMapping("/contents/{contentId}/reviews")
    public ResponseEntity<ReviewResponseDto> save(@PathVariable Long contentId, @RequestBody ReviewCreateRequestDto requestDto) {

        Long memberId = 1L;

        ReviewResponseDto reviewResponseDto = reviewService.save(contentId, memberId, requestDto);

        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }


    //Review 전체 조회 API
    @GetMapping("/contents/{contentId}/reviews")
    public List<ReviewResponseDto> getReviews(@PathVariable Long contentId) {

        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviews(contentId);

        return reviewResponseDtoList;
    }

    //
    @GetMapping("/contents/{contentId}}/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> findById(@PathVariable Long id) {


    }



}
