package com.nbcamp.mypocketmovieapi.dto.content;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TmdbApiClient {  // -> Service로 옮기기
    private final RestClient tmdbRestClient;

    public ContentDetailList searchMovies(String query) {
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
                .body(ContentDetailList.class);
    }
}