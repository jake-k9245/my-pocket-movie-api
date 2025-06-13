package com.nbcamp.mypocketmovieapi.dto.review;


import lombok.Getter;

@Getter
public class ReviewCreateRequestDto {

    private final int rating;
    private final String text;

    public ReviewCreateRequestDto(int rating, String text) {

        this.rating = rating;
        this.text = text;
    }

}
