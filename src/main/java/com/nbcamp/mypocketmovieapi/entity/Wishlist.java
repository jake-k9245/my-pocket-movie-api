package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "wishlists")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", length = 255)
    private String externalId;

    @ManyToOne(fetch = FetchType.LAZY) // 위시리스트는 회원 한명이 여러개 등록할 수 있다.
    @JoinColumn(name = "member_id", nullable = false)
    private Member member; // member_id 필드에서 가져온 회원 번호를 토대로 만든 Entity 객체, 1 (members의 테이블의 id) Long id = 1;
    // private Long memberId; 만약 이렇게 필드를 선언해서 회원의 번호를 가져온다면 회원의 정보가 필요할 때, 이 번호로 회원을 조회하는 코드를 추가적으로 작성해야한다.
    // 하지만 이렇게 Entity 클래스를 필드로 가지고 있으면 우리가 회원의 정보가 필요할 때, getMember() 즉 객체를 호출하면 자동으로 DB에 id를 토대로 JPA가 회원 정보를 조회한다.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;
    // private Long contentId;

    // 생성자 - 객체를 생성할 때, new Wishlist("externalId", new Member(), new Content());
    public Wishlist(String externalId, Member member, Content content) {
        this.externalId = externalId;
        this.member = member;
        this.content = content;
    }

}