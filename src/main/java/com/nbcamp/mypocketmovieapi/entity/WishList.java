package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wishlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Builder
    public Wishlist(Member member, Content content, String externalId) {
        this.member = member;
        this.content = content;
        this.externalId = externalId;
    }
}
