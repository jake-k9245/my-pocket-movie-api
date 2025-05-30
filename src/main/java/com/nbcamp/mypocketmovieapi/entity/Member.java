package com.nbcamp.mypocketmovieapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Email 어노테이션 한계로 패턴 정규식 사용
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    @Column(nullable = false,
            unique = true,
            length = 254)
    private String email;

    @Column(nullable = false,
            length = 100)
    private String password;

    @Column(nullable = false,
            length = 20)
    private String nickname;


    // 진환님 생성한 contents list
    @OneToMany(mappedBy = "content_id",
        orphanRemoval = true,
        cascade = CascadeType.ALL)
    List<Content> createdContentsList;


}
