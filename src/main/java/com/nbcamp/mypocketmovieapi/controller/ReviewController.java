package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.dto.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/contents/{contentId}/reviews")
    public ResponseEntity<ReviewResponseDto> save(@PathVariable Long contentId, @RequestBody ReviewCreateRequestDto requestDto) {
        Long memberId = 1L;
        ReviewResponseDto reviewResponseDto = reviewService.save(contentId, memberId, requestDto);
        return new ResponseEntity<>(reviewResponseDto, HttpStatus.CREATED);
    }

}
