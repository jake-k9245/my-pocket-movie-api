package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.config.PasswordEncoder;
import com.nbcamp.mypocketmovieapi.dto.member.*;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.member.SignInInvalidPassword;
import com.nbcamp.mypocketmovieapi.exception.member.UpdateMismatchPassword;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    public CreatedMemberResponseDto createMember(String email, String rawPassword, String nickname){

        // 이메일 중복 확인?
        if(memberJpaRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(CommonCode.FAIL_DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member createdMember = new Member(email, encodedPassword, nickname);
        Member savedMember = memberJpaRepository.save(createdMember);

        // 보통 필요없음
        return new CreatedMemberResponseDto(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getNickname()
        );
    }

    // 로그인
    public SignInResponseDto signIn(SignInRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(CommonCode.FAIL_INVALID_EMAIL));

        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new UpdateMismatchPassword(CommonCode.FAIL_UPDATE_MISMATCH_PASSWORD);
        }

        return new SignInResponseDto(member.getId(), member.getEmail(), member.getNickname());
    }



    // 로그아웃은 컨트롤러에만 위치


    // 사용자 정보 조회
    public MemberProfileDto getMemberProfile(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND));
        return new MemberProfileDto(member);
    }


    // 사용자 정보 수정
    public MemberProfileDto  updateMemberProfile(Long memberId, UpdateMemberProfileDto updateMemberProfileDto) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND));

        member.updateNickname(updateMemberProfileDto.getNickname());
        memberJpaRepository.save(member);

        return new MemberProfileDto(member);
    }


    // 비밀번호 변경
    public void changeMemberPassword(Long memberId, ChangeMemberPasswordDto changeMemberPasswordDto) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow( () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(changeMemberPasswordDto.getOldPassword(), member.getPassword())){
            throw new SignInInvalidPassword(CommonCode.FAIL_SIGNIN_INVALID_PASSWORD);
        }

        member.updatePassword(passwordEncoder.encode(changeMemberPasswordDto.getNewPassword()));
        memberJpaRepository.save(member);

    }



    // 회원 탈퇴
    public void deleteMember(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND));

        memberJpaRepository.delete(member);
    }

}

