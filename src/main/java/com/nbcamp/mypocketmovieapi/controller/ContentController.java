package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import com.nbcamp.mypocketmovieapi.dto.content.ContentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.content.ContentResponseDto;
import com.nbcamp.mypocketmovieapi.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<CommonResponse<List<ContentDetail>>> searchMovies(@RequestParam String query) {
        List<ContentDetail> results = contentService.findMoviesByKeyword(query);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, results));
    }

    // 2. 검색 데이터 기반 콘텐츠 등록
    @PostMapping
    public ResponseEntity<CommonResponse<ContentResponseDto>> createContent(
            @RequestBody ContentRequestDto requestDto,
            @SigninMember Long memberId
    ) {
        ContentResponseDto response = contentService.createContent(requestDto, memberId);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, response));
    }

    // 3. 목록 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<ContentResponseDto>>> getAllContent(@SigninMember Long memberId) {
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, contentService.findAllContent(memberId)));
    }

    // 4. 콘텐츠 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ContentResponseDto>> getContent(@PathVariable Long id) {
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, contentService.getContentById(id)));
    }

    // 5. 컨텐츠 삭제
    @DeleteMapping("/{contentId}")
    public ResponseEntity<CommonResponse<Void>> deleteContent (
            @SigninMember Long memberId,
            @RequestParam Long contentId
    ) {
        contentService.deleteContent(memberId, contentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(CommonResponse.success(CommonCode.SUCCESS));
    }

}
