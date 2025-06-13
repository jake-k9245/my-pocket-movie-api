package com.nbcamp.mypocketmovieapi.service;



import com.nbcamp.mypocketmovieapi.dto.review.ReviewCreateRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.ReviewResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.ReviewUpdateRequestDto;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<ReviewResponseDto> getReviews() {
        // 해당 콘텐츠의 모든 리뷰 조회
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>(); // []

        List<Review> reviewList = reviewRepository.findAll();
        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }
        return reviewResponseDtoList;
    }

   public List<ReviewResponseDto> getReviewsBy(Long contentId) {

        // 리뷰를 작성한 콘텐츠 정보 조회 select * from contents where id = 1; , 기본 제공 메서드
        Content content = contentRepository.findById(contentId).orElseThrow(
                () -> new RuntimeException("해당하는 콘텐츠를 찾지 못하였습니다.")
        );

        // 해당 콘텐츠의 모든 리뷰 조회
        List<ReviewResponseDto> reviewResponseDtoList = new ArrayList<>();

        // select * from reviews where id = 1; // id가 1인 리뷰 데이터 (무조건 1개), id 는 PK 고유 식별값
        // select * from reviews where content_id = 1; => content id 가 1인 리뷰 데이터 (여러 개일 수도 있다)
        List<Review> reviewList = reviewRepository.findByContent(content);
        for (Review review : reviewList) {
            ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review);
            reviewResponseDtoList.add(reviewResponseDto);
        }
        return reviewResponseDtoList;
    }

    public ReviewResponseDto findById(Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당 리뷰를 찾을 수 없습니다.")
        );
        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(findReview);
        return reviewResponseDto;
    }

    @Transactional
    public void updateReviews(Long memberId, Long reviewId, ReviewUpdateRequestDto requestDto) {
        // 이 요청을 한 회원의 id = memberId
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        Review findReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당 리뷰를 찾을 수 없습니다.")
        );

        if (!Objects.equals(findMember.getId(), findReview.getMember().getId())) {
            throw new RuntimeException("작성자만 리뷰를 수정할 수 있습니다.");
        }

//        findReview.update(requestDto.getText(), requestDto.getRating());

        reviewRepository.save(findReview); // insert, update
    }

    public void deleteReview(Long memberId, Long reviewId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        Review findReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당 리뷰를 찾을 수 없습니다.")
        );

        if (!Objects.equals(findMember.getId(), findReview.getMember().getId())) {
            throw new RuntimeException("작성자만 리뷰를 삭제할 수 있습니다.");
        }

        reviewRepository.delete(findReview);
    }

}
