package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.SaveCommentRequestDto;
import com.nbcamp.mypocketmovieapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


}
