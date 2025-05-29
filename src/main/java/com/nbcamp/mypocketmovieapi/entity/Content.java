package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "contents")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "poster_path")
    private String posterPath;

    @Column(name = "overview")
    private String overView;

    @Column(name = "release_date")
    private Integer releaseDate;

    @Column(name = "created_at", updatable = false) // 수정
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
