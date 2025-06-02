package com.nbcamp.mypocketmovieapi.controller;


import com.nbcamp.mypocketmovieapi.dto.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contents/{contentsId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewCreateRequestDto requestDto) {

        ReviewResponseDto reviewResponseDto = reviewService.save(requestDto.getRating(), requestDto.getText());

        return new ResponseEntity<>(ReviewResponseDto, HttpStatus.CREATED);
    }

}
