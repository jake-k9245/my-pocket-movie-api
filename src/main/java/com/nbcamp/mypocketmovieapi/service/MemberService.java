package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.members.ResponseDtoCreatedMember;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final Member member;
    private final ResponseDtoCreatedMember responseDtoCreatedMember;

    // 회원가입
    public ResponseDtoCreatedMember createMember(String email, String password, String nickname){

        Member createdMember = member(email, password, nickname);
        Member savedMember = memberJpaRepository.save(createdMember);
        ResponseDtoCreatedMember responseDtoCreatedMember1 = ;
        return ;
    }

    // 로그인


    // 로그아웃


    // 사용자 정보 조회


    // 사용자 정보 수정


    // 회원 탈퇴


}
