package com.nbcamp.mypocketmovieapi.dto.member;

import com.nbcamp.mypocketmovieapi.entity.Member;
import lombok.Getter;

@Getter
public class MemberProfileDto {
    private Long id;
    private String email;
    private String nickname;

    public MemberProfileDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }

}
