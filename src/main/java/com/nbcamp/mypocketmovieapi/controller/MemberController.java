package com.nbcamp.mypocketmovieapi.controller;

import com.nbcamp.mypocketmovieapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

}
