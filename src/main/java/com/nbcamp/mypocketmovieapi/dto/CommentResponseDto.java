package com.nbcamp.mypocketmovieapi.dto;

import com.nbcamp.mypocketmovieapi.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long reviewId;
    private Long commentId;
    private String text;

    public CommentResponseDto(Comment comment) {
        this.reviewId = comment.getReview().getId();
        this.commentId = comment.getId();
        this.text = comment.getText();
    }
}
