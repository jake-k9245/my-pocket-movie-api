package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.dto.content.*;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.exception.content.ContentNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.content.DuplicateContentException;
import com.nbcamp.mypocketmovieapi.exception.content.UnAuthorizedContentException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContentService {

    private final TmdbApiClient tmdbApiClient;
    private final ContentJpaRepository contentJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    /**
     * "results": [ => List
     *     { => Object 1개
     *       "id": 838209,
     *       "title": "파묘",
     *       "overview": "미국 LA, 거액의 의뢰를 받은 무당 화림과 봉길은 기이한 병이 대물림되는 집안의 장손을 만난다. 조상의 묫자리가 화근임을 알아챈 화림은 이장을 권하고, 돈 냄새를 맡은 최고의 풍수사 상덕과 장의사 영근이 합류한다. 절대 사람이 묻힐 수 없는 악지에 자리한 기이한 묘. 상덕은 불길한 기운을 느끼고 제안을 거절하지만, 화림의 설득으로 결국 파묘가 시작되고… 나와서는 안될 것이 나왔다.",
     *       "release_date": "2024-02-22",
     *       "popularity": 10.1307,
     *       "poster_path": "/tw0i3kkmOTjDjGFZTLHKhoeXVvA.jpg",
     *     }
     *   ],
     */

    // 1. TMDB 영화 검색 로직
    public List<ContentDetail> findMoviesByKeyword(String keyword) {
        ContentDetailList results = tmdbApiClient.searchMovies(keyword);
        return results.getResults();
    }

    // 2. 검색 데이터 콘텐츠 등록 로직
    public ContentResponseDto createContent(ContentRequestDto requestDto, Long memberId) {

        Member member = getFindMember(memberId);

        List<Content> duplicated = contentJpaRepository.findDistinctByExternalIdAndMember_Id(
                requestDto.getExternalId(), memberId
        );

        if (!duplicated.isEmpty()) {
            throw new DuplicateContentException(CommonCode.FAIL_DUPLICATE_CONTENT);
        }

        Content content = new Content(
                member,
                requestDto.getExternalId(),
                requestDto.getTitle(),
                requestDto.getPosterPath(),
                requestDto.getOverview(),
                requestDto.getReleaseDate(),
                requestDto.getPopularity()
        );
        Content savedContent = contentJpaRepository.save(content);
        return ContentResponseDto.fromEntity(savedContent);
    }

    // 3. 목록 조회 로직 (사용자가 등록한)
    public List<ContentResponseDto> findAllContent(Long memberId) {
        Member member = getFindMember(memberId);
        return contentJpaRepository.findByMember(member).stream()
                .map(ContentResponseDto::fromEntity)
                .toList();
    }

    // 4. 콘텐츠 단건 조회 로직
    public ContentResponseDto getContentById(Long id) {
        Content content = contentJpaRepository.findById(id)
                .orElseThrow(() -> new ContentNotFoundException(CommonCode.FAIL_CONTENT_NOT_FOUND));
        return ContentResponseDto.fromEntity(content);
    }

    // 5. 콘텐츠 삭제 로직
    public void deleteContent(Long memberId, Long contentId) {
        Member member = getFindMember(memberId);
        Content content = contentJpaRepository.findById(contentId)
                .orElseThrow(() -> new ContentNotFoundException(CommonCode.FAIL_CONTENT_NOT_FOUND));
        if (!Objects.equals(member.getId(),content.getMember().getId())){
            throw new UnAuthorizedContentException(CommonCode.FAIL_UNAUTHORIZED_CONTENT_DELETION);
        }
        contentJpaRepository.delete(content);
    }

    private Member getFindMember(Long memberId) {
        return memberJpaRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );
    }
}