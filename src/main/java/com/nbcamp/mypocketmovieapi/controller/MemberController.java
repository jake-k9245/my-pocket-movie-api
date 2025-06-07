package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.dto.member.CreatedMemberRequestDto;
import com.nbcamp.mypocketmovieapi.dto.member.CreatedMemberResponseDto;
import com.nbcamp.mypocketmovieapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
