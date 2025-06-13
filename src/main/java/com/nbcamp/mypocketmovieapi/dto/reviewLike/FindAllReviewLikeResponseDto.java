package com.nbcamp.mypocketmovieapi.dto.reviewLike;


import com.nbcamp.mypocketmovieapi.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindAllReviewLikeResponseDto {
    private final Long contentId;
    private final Long reviewId;
    private final String text;
    private final int rating;

    public FindAllReviewLikeResponseDto(Review review) {
        this.contentId = review.getContent().getId();
        this.reviewId = review.getId();
        this.text = review.getText();
        this.rating = review.getRating();
    }
}
