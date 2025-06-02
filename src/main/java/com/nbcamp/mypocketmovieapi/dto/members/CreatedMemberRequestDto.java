package com.nbcamp.mypocketmovieapi.dto.members;

import lombok.Getter;

@Getter
public class CreatedMemberRequestDto {

    private String email;
    private String password;
    private String nickname;

}
