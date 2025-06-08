package com.nbcamp.mypocketmovieapi.dto.content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentDetail {

    private int id;
    private String title;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    private double popularity;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("poster_path")
    private String posterPath;

}
