package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.content.ContentDetail;
import com.nbcamp.mypocketmovieapi.dto.content.ContentDetailList;
import com.nbcamp.mypocketmovieapi.dto.content.ContentRequestDto;
import com.nbcamp.mypocketmovieapi.dto.content.ContentResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ContentResponseDto createContent(ContentRequestDto requestDto, Long memberid) {

        Member member = memberJpaRepository.findById(memberid).orElseThrow(
                () -> new RuntimeException("해당 사용자는 존재하지 않습니다.")
        );

        List<Content> duplicated = contentJpaRepository.findDistinctByExternalIdAndMember_Id(
                requestDto.getExternalId(), memberid
        );

        if (!duplicated.isEmpty()) {
            throw new IllegalArgumentException("이미 등록된 콘텐츠입니다.");
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
        Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당 사용자는 존재하지 않습니다.")
        );
        return contentJpaRepository.findByMember(member).stream()
                .map(ContentResponseDto::fromEntity)
                .toList();
    }

    // 4. 콘텐츠 단건 조회 로직
    public ContentResponseDto getContentById(Long id) {
        Content content = contentJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 콘텐츠가 존재하지 않습니다."));
        return ContentResponseDto.fromEntity(content);
    }
}














