package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import com.nbcamp.mypocketmovieapi.dto.content.ContentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.content.ContentResponseDto;
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
    public ResponseEntity<List<ContentDetail>> searchMovies(@RequestParam String query) {

        List<ContentDetail> results = contentService.findMoviesByKeyword(query);
        return ResponseEntity.ok(results);
    }

    // 2. 검색 데이터 기반 콘텐츠 등록
    @PostMapping
    public ResponseEntity<ContentResponseDto> createContent(@RequestBody ContentRequestDto requestDto) {
        Long memberId = 1L;
        ContentResponseDto response = contentService.createContent(requestDto, memberId);
        return ResponseEntity.ok(response);
    }

    // 3. 목록 조회
    @GetMapping
    public ResponseEntity<List<ContentResponseDto>> getAllContent() {
        Long memberId = 1l;
        return ResponseEntity.ok(contentService.findAllContent(memberId));
    }

    // 4. 콘텐츠 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<ContentResponseDto> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(contentService.getContentById(id));
    }
}
