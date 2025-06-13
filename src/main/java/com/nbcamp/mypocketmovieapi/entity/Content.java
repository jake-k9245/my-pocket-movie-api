package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "contents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // JPA를 활용한 객체 연관관계 (더 많이 사용되는 형태)

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "overview")
    private String overview;

    @Column(name = "release_date")
    private LocalDate releaseDate; // LocalDate = 1999-09-09와 같은 날짜 형식

    @Column(name = "popularity")
    private double popularity;

    public Content(Member member, String externalId, String title, String posterPath, String overview, LocalDate releaseDate, double popularity) {
        this.member = member;
        this.externalId = externalId;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }
}
