package com.nbcamp.mypocketmovieapi.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequestDto {

    
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

}
