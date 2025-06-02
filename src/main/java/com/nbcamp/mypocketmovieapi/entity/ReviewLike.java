package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "review_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // fetch = FetchType.LAZY : 연관된 Entity를 즉시 Loading 하지 않고 필요할 때만 조회
    // ReviewLike : Member = N : 1
    // memberId를 Foreign Key로 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // fetch = FetchType.LAZY : 연관된 Entity를 즉시 Loading 하지 않고 필요할 때만 조회
    // ReviewLike : Review = N : 1
    // reviewId를 Foreign Key로 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    // Bulider Pattern 적용
    @Builder
    public ReviewLike(Member member, Review review) {
        this.member = member;
        this.review = review;
    }



}
