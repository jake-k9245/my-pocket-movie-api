package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.members.CreatedMemberResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    // 회원가입
    public CreatedMemberResponseDto createMember(String email, String password, String nickname){

        Member createdMember = new Member(email, password, nickname);
        Member savedMember = memberJpaRepository.save(createdMember);
        CreatedMemberResponseDto responseDto = new CreatedMemberResponseDto(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getNickname()
        );

        return responseDto;
        // Q) 직접 반환(inline return)하면 가독성이 떨어져보이는데, 내가 초보라 그런건가? 이렇게 변수에 담아서 반환하는게 이 경우에는 안 좋나?
    }

    // 로그인


    // 로그아웃


    // 사용자 정보 조회


    // 사용자 정보 수정


    // 회원 탈퇴


}
