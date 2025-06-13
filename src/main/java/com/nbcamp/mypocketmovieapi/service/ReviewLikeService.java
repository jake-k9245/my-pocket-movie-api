package com.nbcamp.mypocketmovieapi.service;


import com.nbcamp.mypocketmovieapi.dto.reviewLike.FindAllReviewLikeResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.entity.ReviewLike;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewLikeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewLikeService {

    private final ReviewLikeJpaRepository reviewLikeRepository;
    private final MemberJpaRepository memberRepository;
    private final ReviewJpaRepository reviewRepository;

    public void toggleReviewLike(Long memberId, Long reviewId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        Review findReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당 리뷰를 찾을 수 없습니다.")
        );

        // 이미 좋아요를 눌렀는지 확인하기
        Optional<ReviewLike> existedReivewLike = reviewLikeRepository.findByMemberAndReview(findMember, findReview);

        if(existedReivewLike.isPresent()) {
            // 이미 좋아요를 누른 경우 -> 좋아요 취소(삭제)
            reviewLikeRepository.delete(existedReivewLike.get());
        } else {
            // 좋아요를 누르지 않은 경우 -> 좋아요 (등록)
            ReviewLike reviewLike = ReviewLike.builder()
                    .member(findMember)
                    .review(findReview)
                    .build();

            reviewLikeRepository.save(reviewLike);
        }
    }

    @Transactional
    public List<FindAllReviewLikeResponseDto> findAllReviewLike(Long memberId) {

        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        // select * from review_likes where member_id = 1;
        List<ReviewLike> reviewLikes = reviewLikeRepository.findByMember(findMember);

        List<FindAllReviewLikeResponseDto> responseDtoList = new ArrayList<>();
        for (ReviewLike reviewLike : reviewLikes) {
            // ReviewLike -> FindAllReviewLikeResponseDto
            FindAllReviewLikeResponseDto responseDto = new FindAllReviewLikeResponseDto(reviewLike.getReview());
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }
}
