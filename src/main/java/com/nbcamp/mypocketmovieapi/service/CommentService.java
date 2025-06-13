package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.review.CommentResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.UpdateCommentRequestDto;
import com.nbcamp.mypocketmovieapi.entity.Comment;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
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

    // memberId, reviewId, 저장할 데이터(text)
    public void saveComments(Long memberId, Long reviewId, SaveCommentRequestDto requestDto) {
        // 작성하려는 Member가 존재하는지 확인 및 Entity 객체 가져오기, MemberJpaRepository findById() => select * from members where id = ?
        Member member =  memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );

        // 작성대상인 Review가 존재하는지 확인 및 Entity 객체 가져오기, ReviewJpaRepository findById() => select * from reviews where id = ?
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당하는 리뷰가 존재하지 않습니다.")
        );

        // Comment Entity 객체를 생성 (Member, Reivew, 저장데이터(requestDto)) => Comment 객체 1개가 곧 테이블의 1row, 엑셀의 1줄(행)
        Comment comment = Comment.builder().member(member).review(review).text(requestDto.getText()).build();

        // CommentJpaRepository => 데이터베이스에 접근해서 우리 대신 SQL을 작성 및 excute 하는 객체
        // CommentJpaRepository save(Comment Entity의 객체) 라는 메서드가 존재 (insert=저장)
        commentJpaRepository.save(comment); // => insert into
    }

    public void updateComments(Long memberId, Long commentId, UpdateCommentRequestDto requestDto) {
        // 수정 하려는 회원의 정보를 조회
        Member member =  memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );
        // 수정 하려는 댓글의 정보를 조회
        Comment comment = commentJpaRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당하는 댓글이 존재하지 않습니다.")
        );
        // 수정 하려는 회원의 id와 수정 하려는 댓글을 작성한 회원의 id가 일치하는 경우에만 수정 가능
        if(!Objects.equals(member.getId(), comment.getMember().getId())) {
            // 수정 하려는 회원의 id와 수정 하려는 댓글을 작성한 회원의 id가 일치하지 않는 경우에 실행됨.
            throw new RuntimeException("댓글을 작성한 회원만 수정이 가능합니다.");
        }
        // 수정 진행
        comment.updateText(requestDto.getText());
        commentJpaRepository.save(comment); // insert & update (새로 생성되는 entity는 insert, DB에서 조회해온 entity는 update)
    }

    public void deleteComments(Long memberId, Long commentId) {
        // 삭제 하려는 회원의 정보를 조회
        Member member =  memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당하는 멤버가 존재하지 않습니다.")
        );
        // 삭제 하려는 댓글의 정보를 조회
        Comment comment = commentJpaRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("해당하는 댓글이 존재하지 않습니다.")
        );
        // 삭제 하려는 회원의 id와 삭제 하려는 댓글을 작성한 회원의 id가 일치하는 경우에만 삭제 가능
        if(!Objects.equals(member.getId(), comment.getMember().getId())) {
            // 삭제 하려는 회원의 id와 삭제 하려는 댓글을 작성한 회원의 id가 일치하지 않는 경우에 실행됨.
            throw new RuntimeException("댓글을 작성한 회원만 수정이 가능합니다.");
        }
        commentJpaRepository.delete(comment); // delete
    }

    public List<CommentResponseDto> getComments(Long reviewId) {
        // 댓글이 달려있는 리뷰 정보 조회
        Review review = reviewJpaRepository.findById(reviewId).orElseThrow(
                () -> new RuntimeException("해당하는 리뷰가 존재하지 않습니다.")
        );
        // 해당 리뷰에 달린 모든 댓글 조회
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        List<Comment> commentList = commentJpaRepository.findByReview(review);
        for (Comment comment : commentList) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            responseDtoList.add(commentResponseDto);
        }

        return responseDtoList;
    }
}
