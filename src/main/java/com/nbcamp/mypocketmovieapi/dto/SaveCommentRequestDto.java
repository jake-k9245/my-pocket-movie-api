package com.nbcamp.mypocketmovieapi.dto;

import lombok.Getter;

@Getter
public class SaveCommentRequestDto {
    // { text : "댓글 작성" } => new SaveCommentRequestDto()
    private String text; // <= 댓글 작성
}
