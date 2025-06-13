package com.nbcamp.mypocketmovieapi.dto.member;

import lombok.Getter;

@Getter
public class SignInResponseDto {

    private Long id;
    private String email;
    private String nickname;


    public SignInResponseDto(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

}
