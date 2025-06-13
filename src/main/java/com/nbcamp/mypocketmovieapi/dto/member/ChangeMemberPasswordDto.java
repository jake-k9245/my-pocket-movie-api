package com.nbcamp.mypocketmovieapi.dto.member;

import lombok.Getter;

@Getter
public class ChangeMemberPasswordDto {
    private String oldPassword;
    private String newPassword;
}
