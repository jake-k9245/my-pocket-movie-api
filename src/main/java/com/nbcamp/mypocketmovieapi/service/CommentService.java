package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.dto.review.CommentResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.UpdateCommentRequestDto;
import com.nbcamp.mypocketmovieapi.entity.Comment;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.exception.comment.CommentNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.comment.UnAuthorizedCommentException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.review.ReviewNotFoundException;
import com.nbcamp.mypocketmovieapi.repository.CommentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberJpaRepository memberJpaRepository;
    private final ReviewJpaRepository reviewJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    public void saveComments(Long memberId, Long reviewId, SaveCommentRequestDto requestDto) {
        Member member = getFindMember(memberId);
        Review review = getFindReview(reviewId);
        Comment comment = Comment.builder().member(member).review(review).text(requestDto.getText()).build();
        commentJpaRepository.save(comment);
    }

    public void updateComments(Long memberId, Long commentId, UpdateCommentRequestDto requestDto) {
        Member member = getFindMember(memberId);
        Comment comment = getFindComment(commentId);

        if(!Objects.equals(member.getId(), comment.getMember().getId())) {
            throw new UnAuthorizedCommentException(CommonCode.FAIL_UNAUTHORIZED_COMMENT_MODIFICATION);
        }

        comment.updateText(requestDto.getText());
        commentJpaRepository.save(comment);
    }

    public void deleteComments(Long memberId, Long commentId) {
        Member member = getFindMember(memberId);
        Comment comment = getFindComment(commentId);

        if(!Objects.equals(member.getId(), comment.getMember().getId())) {
            throw new UnAuthorizedCommentException(CommonCode.FAIL_UNAUTHORIZED_COMMENT_DELETION);
        }

        commentJpaRepository.delete(comment);
    }

    public List<CommentResponseDto> getComments(Long reviewId) {
        Review review = getFindReview(reviewId);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        List<Comment> commentList = commentJpaRepository.findByReview(review);
        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            responseDtoList.add(commentResponseDto);
        }

        return responseDtoList;
    }

    private Review getFindReview(Long reviewId) {
        return reviewJpaRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException(CommonCode.FAIL_REVIEW_NOT_FOUND)
        );
    }

    private Member getFindMember(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );
    }

    private Comment getFindComment(Long commentId) {
        return commentJpaRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(CommonCode.FAIL_COMMENT_NOT_FOUND)
        );
    }

}
