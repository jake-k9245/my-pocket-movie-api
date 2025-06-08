package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import com.nbcamp.mypocketmovieapi.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    // 1. TMDB 영화 검색용 API
    @GetMapping("/search")
    public ResponseEntity<List<ContentDetail>> searchMovies(@RequestParam String keyword) {
        List<ContentDetail> results = ContentService.findMoviesByKeyword(keyword);
        return ResponseEntity.ok(results);
    }

//    // 검색 데이터 기반 콘텐츠 등록 2번
//    @PostMapping("/api/contents")
//    public ResponseEntity<ContentResponseDto> createContent(@RequestBody ContentRequestDto requestDto) {
//        return ResponseEntity.ok(contentService.createContent(requestDto));
//    }
//
//    // 목록 조회 3번
//    @GetMapping("/api/contents")
//    public ResponseEntity<ContentResponseDto>createContent(@RequestBody ContentRequestDto requestDto) {
//        return ResponseEntity.ok(contentService.createContent(requestDto));
//    }

//    // 콘텐츠 정보 조회 4번, 완료 - 수정 x
//    @GetMapping("/api/contents/{id}")
//    public ResponseEntity<ContentResponseDto> getContent(@PathVariable Long id) {
//        return ResponseEntity.ok(contentService.getContentById(id));
//    }
}
