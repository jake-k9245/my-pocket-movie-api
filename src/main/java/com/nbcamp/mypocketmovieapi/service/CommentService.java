package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.entity.Comment;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.repository.CommentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

}
