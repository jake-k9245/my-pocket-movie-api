package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "reviews")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // fetch = FetchType.LAZY : 연관된 Entity를 즉시 Loading 하지 않고 필요할 때만 조회
    // Review : Member = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // fetch = FetchType.LAZY : 연관된 Entity를 즉시 Loading 하지 않고 필요할 때만 조회
    // Review : Content = N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(nullable = false)
    private Integer rating;

    // @Lob : 대용량 Text를 저장하는데 사용하는 annotation
    @Lob
    private String text;

    // Bulider Pattern 적용 Annotation
    @Builder
    public Review(Member member, Content content, Integer rating, String text) {
        this.member = member;
        this.content = content;
        this.rating = rating;
        this.text = text;
    }

}

