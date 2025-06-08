package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import com.nbcamp.mypocketmovieapi.dto.content.TmdbApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final TmdbApiClient tmdbApiClient;

    public static List<ContentDetail> findMoviesByKeyword(String keyword) {
        ContentDetail[] results = tmdbApiClient.searchMovies(keyword);
        return Arrays.asList(results);
    }
}

//    // 컨트롤러 4번 콘텐츠 단건 조회 + 목록 조회
//    public ContentResponseDto getContentById(Long id) {
//        Content content = contentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 콘텐츠가 존재하지 않습니다."));
//        return ContentResponseDto.fromEntity(content);
//    }
