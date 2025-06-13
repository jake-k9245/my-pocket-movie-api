package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.member.*;
import com.nbcamp.mypocketmovieapi.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody CreatedMemberRequestDto request) {
        CreatedMemberResponseDto response = memberService.createMember(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "code", 201,
                "status", "CREATED",
                "data", response
        ));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto request, HttpSession session) {
        SignInResponseDto response = memberService.signIn(request);
        session.setAttribute("signinMemberId", response.getId());
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "status", "OK",
                "data", response
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "status", "OK",
                "data", "로그아웃 완료되었습니다"
        ));
    }

    @PostMapping("/me")
    public ResponseEntity<?> getMemberInfo(HttpSession session) {
        Long memberId = (Long)session.getAttribute("SigninMemberId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "code", 401,
                    "status", "UNAUTHORIZED",
                    "data", "로그인이 되어있지 않습니다."
            ));
        }

        MemberProfileDto profile = memberService.getMemberProfile(memberId);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "status", "OK",
                "data", profile
        ));
    }

    @PutMapping("/me")
    public ResponseEntity<?> updateMemberInfo(HttpSession session, @RequestBody UpdateMemberProfileDto updateMemberProfileDto) {
        Long memberId = (Long)session.getAttribute("signinMemberId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "code", 401,
                    "status", "UNAUTHORIZED",
                    "data", "로그인이 되어있지 않습니다."
            ));
        }

        MemberProfileDto updatedProfile = memberService.updateMemberProfile(memberId, updateMemberProfileDto);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "status", "OK",
                "data", updatedProfile
        ));
    }

    @PutMapping("/me/password")
    public ResponseEntity<?> changeMemberPassword(HttpSession session, @RequestBody ChangeMemberPasswordDto changeMemberPasswordDto) {
        Long memberId = (Long) session.getAttribute("signinMemberId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "code", 401,
                    "status", "UNAUTHORIZED",
                    "data", "로그인이 되어있지 않습니다."
            ));
        }

        memberService.changeMemberPassword(memberId, changeMemberPasswordDto);
        return ResponseEntity.ok(Map.of(
                "code", 200,
                "status", "OK",
                "data", "비밀번호 변경 완료되었습니다"
        ));
    }


    @DeleteMapping("/me")
    public ResponseEntity<?> deleteMember(HttpSession session) {
        Long memberId = (Long) session.getAttribute("signinMemberId");

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "code", 401,
                    "status", "UNAUTHORIZED",
                    "data", "로그인이 되어있지 않습니다."
            ));
        }

        memberService.deleteMember(memberId);
        return ResponseEntity.ok(Map.of(
                "code", 204,
                "status", "NO_CONTENT"
        ));

    }




}
