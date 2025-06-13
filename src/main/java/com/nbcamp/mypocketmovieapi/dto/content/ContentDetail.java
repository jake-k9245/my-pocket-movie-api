package com.nbcamp.mypocketmovieapi.dto.content;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

import java.time.LocalDate;

@Getter

public class ContentDetail {

    @JsonSetter("id")
    private int externalId;
    private String title;
    private String overview;
    @JsonSetter("release_date")
    private LocalDate releaseDate;
    private double popularity;
    @JsonSetter("poster_path")
    private String posterPath;

}