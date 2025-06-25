package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.JwtUtil;
import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.dto.member.*;
import com.nbcamp.mypocketmovieapi.security.UserDetailsImpl;
import com.nbcamp.mypocketmovieapi.service.MemberService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CommonResponse<CreatedMemberResponseDto>> signup(@RequestBody CreatedMemberRequestDto request) {
        CreatedMemberResponseDto response = memberService.createMember(
                request.getEmail(),
                request.getPassword(),
                request.getNickname()
        );
        // static 이니까 바로 바로 부를 수 있음
        CommonResponse<CreatedMemberResponseDto> commonResponse = CommonResponse.success(CommonCode.SUCCESS_SIGNUP, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(commonResponse);
    }


    @GetMapping("/me")
    public ResponseEntity<CommonResponse<MemberProfileDto>> getMemberInfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) { //  SigninArgumentResolver가 어노테이션 돌게 만들어줌!

        MemberProfileDto profile = memberService.getMemberProfile(userDetails.getMember().getId());
        CommonResponse<MemberProfileDto> commonResponse = CommonResponse.success(CommonCode.SUCCESS, profile);

        return ResponseEntity.ok(commonResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<CommonResponse<MemberProfileDto>> updateMemberInfo(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody UpdateMemberProfileDto updateMemberProfileDto) {

        MemberProfileDto updatedProfile = memberService.updateMemberProfile(userDetails.getMember().getId(), updateMemberProfileDto);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS, updatedProfile));
    }

    @PatchMapping("/me/password")
    public ResponseEntity<CommonResponse<Void>> changeMemberPassword(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ChangeMemberPasswordDto changeMemberPasswordDto) {

        // AuthenticationInterceptor 에서 이걸 다 해주기때문에 컨트롤러에서 체크할 필요가 없어짐
//        if (memberId == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
//                    "code", 401,
//                    "status", "UNAUTHORIZED",
//                    "data", "로그인이 되어있지 않습니다."
//            ));
//        }

        memberService.changeMemberPassword(userDetails.getMember().getId(), changeMemberPasswordDto);
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS));
    }

    @DeleteMapping("/me")
    public ResponseEntity<CommonResponse<Void>> deleteMember(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.deleteMember(userDetails.getMember().getId());
        return ResponseEntity.ok(CommonResponse.success(CommonCode.SUCCESS));
    }

}
