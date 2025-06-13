package com.nbcamp.mypocketmovieapi.dto.review;


import com.nbcamp.mypocketmovieapi.entity.Review;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long reviewId;
    private Long contentId;
    private Long memberId;
    private int rating;
    private String text;

    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.contentId = review.getContent().getId();
        this.memberId = review.getMember().getId();
        this.rating = review.getRating();
        this.text = review.getText();
    }
}
