package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.member.CreatedMemberResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    // 회원가입
    public CreatedMemberResponseDto createMember(String email, String password, String nickname){

        // 이메일 중복 확인?
        if(memberJpaRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        Member createdMember = new Member(email, password, nickname);
        Member savedMember = memberJpaRepository.save(createdMember);
        return new CreatedMemberResponseDto(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getNickname()
        );
    }

    // 로그인



    // 로그아웃


    // 사용자 정보 조회


    // 사용자 정보 수정


    // 회원 탈퇴


}
