package com.nbcamp.mypocketmovieapi.dto.members;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedMemberResponseDto {
    private Long memberId;
    private String email;
    private String nickname;

}
