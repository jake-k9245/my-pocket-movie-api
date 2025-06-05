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

@Service
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


//        Review review = new Review(findMember, findContent, requestDto.getRating(), requestDto.getText());
        Review review = Review.builder()
                .member(findMember)
                .content(findContent)
                .rating(requestDto.getRating())
                .text(requestDto.getText())
                .build();

        Review savedReview = reviewRepository.save(review);
        return new ReviewResponseDto(savedReview);
    }

}

// RequiredArgsConstructor : final Repository 생성자
// findByIdOrElseThrow 할 때 Id-> Email 등의 변경은 emember 담당자가 설정해주는 부분,
// contentJpa 의존성 추가
//