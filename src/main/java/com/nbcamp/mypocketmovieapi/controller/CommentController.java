package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.review.CommentResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.UpdateCommentRequestDto;
import com.nbcamp.mypocketmovieapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // POST http://localhost:8080/api/reviews/{reviewId}/comments
    // { text : "댓글 작성" } => body
    @PostMapping("/api/reviews/{reviewId}/comments")
    public void saveComments(@PathVariable Long reviewId, @RequestBody SaveCommentRequestDto requestDto) {
        // 누가 작성했는지? => memberId
        Long memberId = 1L;
        commentService.saveComments(memberId, reviewId, requestDto);
    }

    // PUT http://localhost:8080/api/comments/{commentId}
    // { text : "댓글 수정" } => body
    @PutMapping("/api/comments/{commentId}")
    public void updateComments(@PathVariable Long commentId, @RequestBody UpdateCommentRequestDto requestDto) {
        // 댓글을 작성한 회원만 댓글을 수정할 수 있게 해줘야한다.
        Long memberId = 1L;
        commentService.updateComments(memberId, commentId, requestDto);
    }

    // DELETE http://localhost:8080/api/comments/{commentId}
    @DeleteMapping("/api/comments/{commentId}")
    public void deleteComments(@PathVariable Long commentId) {
        // 댓글을 작성한 회원만 댓글을 삭제할 수 있게 해줘야한다.
        Long memberId = 1L;
        commentService.deleteComments(memberId, commentId);
    }

    // GET http://localhost:8080/api/reviews/{reviewId}/comments
    @GetMapping("/api/reviews/{reviewId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long reviewId) {
       List<CommentResponseDto> responseDtoList = commentService.getComments(reviewId);
       return responseDtoList;
    }

}