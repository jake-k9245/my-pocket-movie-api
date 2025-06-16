package com.nbcamp.mypocketmovieapi.service;


import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.dto.reviewLike.FindAllReviewLikeResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.entity.ReviewLike;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.review.ReviewNotFoundException;
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

    public CommonCode toggleReviewLike(Long memberId, Long reviewId) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );

        Review findReview = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(CommonCode.FAIL_REVIEW_NOT_FOUND)
        );

        // 이미 좋아요를 눌렀는지 확인하기
        Optional<ReviewLike> existedReivewLike = reviewLikeRepository.findByMemberAndReview(findMember, findReview);

        if(existedReivewLike.isPresent()) {
            // 이미 좋아요를 누른 경우 -> 좋아요 취소(삭제)
            reviewLikeRepository.delete(existedReivewLike.get());
            return CommonCode.SUCCESS_REVIEW_UNLIKED;
        } else {
            // 좋아요를 누르지 않은 경우 -> 좋아요 (등록)
            ReviewLike reviewLike = ReviewLike.builder()
                    .member(findMember)
                    .review(findReview)
                    .build();

            reviewLikeRepository.save(reviewLike);
            return CommonCode.SUCCESS_REVIEW_LIKED;
        }
    }

    @Transactional
    public List<FindAllReviewLikeResponseDto> findAllReviewLike(Long memberId) {

        Member findMember = memberRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
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
