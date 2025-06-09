package com.nbcamp.mypocketmovieapi.service;


import com.nbcamp.mypocketmovieapi.dto.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewJpaRepository reviewRepository;
    private final MemberJpaRepository memberRepository;
    private final ContentJpaRepository contentRepository;

    public ReviewResponseDto save(Long contentId, Long memberId, ReviewCreateRequestDto requestDto) {

        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        Content findContent = contentRepository.findById(contentId).orElseThrow(
                () -> new RuntimeException("해당하는 콘텐츠가 존재하지 않습니다.")
        );

        Review review = Review.builder()
                .member(findMember)
                .content(findContent)
                .rating(requestDto.getRating())
                .text(requestDto.getText())
                .build();

        Review savedReview = reviewRepository.save(review);
        return new ReviewResponseDto(savedReview);
    }

    public List<ReviewResponseDto> getReviews(Long contentId) {

        // 리뷰를 작성한 콘텐츠 정보 조회
        Content content = contentRepository.findById(contentId).orElseThrow(
                () -> new RuntimeException("해당하는 콘텐츠를 찾지 못하였습니다.")
        );

        // 해당 콘텐츠의 모든 리뷰 조회
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        List<Review> reviewList = reviewRepository.findByContent(content);
        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }
        return reviewResponseDtoList;

    }
}
