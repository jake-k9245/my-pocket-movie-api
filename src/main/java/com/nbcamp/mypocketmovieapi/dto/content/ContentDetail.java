package com.nbcamp.mypocketmovieapi.dto.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDetail {

    private int id;
    private String title;
    private String overview;
    @JsonSetter("release_date")
    private LocalDate releaseDate;
    private double popularity;
    @JsonSetter("poster_path")
    private String posterPath;

}
