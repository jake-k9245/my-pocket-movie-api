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
//    @OneToMany(mappedBy = "content_id", //content id가 되면 안됌! 틀렸음! 정답은 컨텐트쪽에 Member member인 member(컨텐츠의 변수명)를 가르켜야함
//        orphanRemoval = true,
//        cascade = CascadeType.ALL)
//    List<Content> createdContentsList; //리스트를 갖는 형태로 코드 짜면 문제가 생김 그래서 이렇게 짜지 않음 (실무에서는 one to many 잘 안씀 , 엄청 많은데 one to many 다 갖고 있으면 헤비해짐, 예측할 수 없는 쿼리들이 많이 발생 n+1 같은 문제들 발생)

    // 컨텐츠가 멤버 아이디 갖음, 그래서 컨텐츠가 주인임! 주인이 아닌 쪽에서 mapped by 써야함! 상대방 엔터티에서 Member
    /*
    mappedBy 연관 관계의 주인이 아닌 쪽에만 사용해야하며
    mappedBy에서 지정하는 값은 상대방 엔티티(컨텐트)에서 자신(멤버)을 참조한 필드의 이름과 정확하게 일치해야 동작하는 옵션
     */

    // 기본 생성자
    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

}
