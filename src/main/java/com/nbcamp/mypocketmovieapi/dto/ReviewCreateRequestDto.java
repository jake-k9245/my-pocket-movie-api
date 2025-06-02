package com.nbcamp.mypocketmovieapi.dto;


import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {

    private final Integer Rating;

    private final String text;

    public ReviewCreateRequestDto(Integer rating, String text) {

        this.Rating = rating;
        this.text = text;
    }

}
