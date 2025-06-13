package com.nbcamp.mypocketmovieapi.dto;


import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {
//    { "rating": 1, "text": "리뷰 Good" }

    private int rating;
    private String text;

}
