package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.review.CommentResponseDto;
import com.nbcamp.mypocketmovieapi.dto.review.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.review.UpdateCommentRequestDto;
import com.nbcamp.mypocketmovieapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // POST http://localhost:8080/api/reviews/{reviewId}/comments
    // { text : "댓글 작성" } => body
    @PostMapping("/api/reviews/{reviewId}/comments")
    public ResponseEntity<CommonResponse<Void>> saveComments(
            @PathVariable Long reviewId,
            @RequestBody SaveCommentRequestDto requestDto,
            @SigninMember Long memberId
    ) {
        commentService.saveComments(memberId, reviewId, requestDto);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS));
    }

    // PUT http://localhost:8080/api/comments/{commentId}
    // { text : "댓글 수정" } => body
    @PutMapping("/api/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> updateComments(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequestDto requestDto,
            @SigninMember Long memberId
    ) {
        commentService.updateComments(memberId, commentId, requestDto);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS));
    }

    // DELETE http://localhost:8080/api/comments/{commentId}
    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComments(
            @PathVariable Long commentId,
            @SigninMember Long memberId
    ) {
        // 댓글을 작성한 회원만 댓글을 삭제할 수 있게 해줘야한다.
        commentService.deleteComments(memberId, commentId);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS));
    }

    // GET http://localhost:8080/api/reviews/{reviewId}/comments
    @GetMapping("/api/reviews/{reviewId}/comments")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getComments(@PathVariable Long reviewId) {
       List<CommentResponseDto> responseDtoList = commentService.getComments(reviewId);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, responseDtoList));
    }

}
