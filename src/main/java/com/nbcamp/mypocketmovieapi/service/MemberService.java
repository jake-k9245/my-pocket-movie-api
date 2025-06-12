package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.config.PasswordEncoder;
import com.nbcamp.mypocketmovieapi.dto.member.CreatedMemberResponseDto;
import com.nbcamp.mypocketmovieapi.dto.member.SignInRequestDto;
import com.nbcamp.mypocketmovieapi.dto.member.SignInResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.exception.member.DuplicateEmailException;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PostMapping("/api/members")
    public CreatedMemberResponseDto createMember(String email, String rawPassword, String nickname){

        // 이메일 중복 확인?
        if(memberJpaRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        Member createdMember = new Member(email, encodedPassword, nickname);
        Member savedMember = memberJpaRepository.save(createdMember);
        return new CreatedMemberResponseDto(
                savedMember.getId(),
                savedMember.getEmail(),
                savedMember.getNickname()
        );
    }

    // 로그인
    @PostMapping("/api/members/login")
    public SignInResponseDto signIn(SignInRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."))
        if(!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.")
        }

        return new SignInResponseDto(member.getId(), member.getEmail(), member.getNickname());
    }



    // 로그아웃


    // 사용자 정보 조회


    // 사용자 정보 수정


    // 회원 탈퇴


}
