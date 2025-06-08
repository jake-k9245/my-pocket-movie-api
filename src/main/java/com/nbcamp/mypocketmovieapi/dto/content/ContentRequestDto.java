package com.nbcamp.mypocketmovieapi.dto.content;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ContentRequestDto {

    private String externalId;
    private String title;
    private String posterPath;
    private String overview;
    private LocalDate releaseDate;
}

