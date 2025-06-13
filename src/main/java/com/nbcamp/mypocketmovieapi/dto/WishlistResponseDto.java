package com.nbcamp.mypocketmovieapi.dto;

import com.nbcamp.mypocketmovieapi.entity.Wishlist;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WishlistResponseDto {
    private Long id;
    private Long contentId;
    private String email;
    private String externalId;
    private String title;
    private String posterPath;
    private String overview;
    private LocalDate releaseDate;

    public WishlistResponseDto(Wishlist wishlist) {
        this.id = wishlist.getId();
        this.contentId = wishlist.getContent().getId();
        this.email = wishlist.getMember().getEmail();
        this.externalId = wishlist.getExternalId();
    }
}
