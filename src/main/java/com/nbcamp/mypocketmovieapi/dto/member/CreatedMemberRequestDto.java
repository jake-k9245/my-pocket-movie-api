package com.nbcamp.mypocketmovieapi.dto.member;

import lombok.Getter;

@Getter
public class CreatedMemberRequestDto {

    private String email;
    private String password;
    private String nickname;

}
