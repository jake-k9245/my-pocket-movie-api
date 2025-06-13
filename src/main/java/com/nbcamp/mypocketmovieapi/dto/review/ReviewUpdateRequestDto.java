package com.nbcamp.mypocketmovieapi.dto.review;



import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {

    private final int rating;
    private final String text;

    public ReviewUpdateRequestDto(int rating, String text) {
        this.rating = rating;
        this.text = text;
    }
}
