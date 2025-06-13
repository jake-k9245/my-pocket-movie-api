package com.nbcamp.mypocketmovieapi.dto.review;

import lombok.Getter;

@Getter
public class SaveCommentRequestDto {
    // { text : "댓글 작성" } => new SaveCommentRequestDto()
    private String text; // <= 댓글 작성
}
