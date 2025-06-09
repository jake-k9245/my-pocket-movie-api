package com.nbcamp.mypocketmovieapi.dto.content;

import com.nbcamp.mypocketmovieapi.entity.Content;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class ContentResponseDto {

    private String externalId;
    private String title;
    private String posterPath;
    private String overview;
    private LocalDate releaseDate;
    private double popularity;

    public static ContentResponseDto fromEntity(Content content) {
        return ContentResponseDto.builder()
                .externalId(content.getExternalId())
                .title(content.getTitle())
                .posterPath(content.getPosterPath())
                .overview(content.getOverview())
                .releaseDate(content.getReleaseDate())
                .popularity(content.getPopularity())
                .build();

    }
}


