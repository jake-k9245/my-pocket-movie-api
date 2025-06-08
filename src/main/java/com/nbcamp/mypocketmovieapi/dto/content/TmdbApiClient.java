package com.nbcamp.mypocketmovieapi.dto.content;

import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TmdbApiClient {

    private final RestClient tmdbRestClient;

    public ContentDetail[] searchMovies(String query) {
        return tmdbRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", query)
                        .queryParam("include_adult", false)
                        .queryParam("language", "ko-KR")
                        .queryParam("page", 1)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(ContentDetail[].class);
    }
}