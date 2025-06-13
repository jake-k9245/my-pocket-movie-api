package com.nbcamp.mypocketmovieapi.dto;

import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {
    private int rating;
    private String text;

}
