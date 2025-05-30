package com.nbcamp.mypocketmovieapi.dto.members;

import lombok.Getter;

@Getter
public class RequestDtoCreatedMember {

    private String email;
    private String password;
    private String nickname;

}
