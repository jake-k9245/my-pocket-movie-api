package com.nbcamp.mypocketmovieapi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.mypocketmovieapi.JwtUtil;
import com.nbcamp.mypocketmovieapi.common.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor // JwtUtil이 @Component로 빈 등록돼 있어, 자동으로 생성자 주입해줌
@Component // bean으로 등록해줌
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Header에서 순수한 토큰을 가져온다.
        Optional<String> tokenOpt = jwtUtil.extractResolveToken(request);

        // 토큰이 존재하면
       if(tokenOpt.isPresent()) {
           String token = tokenOpt.get();
           if (!jwtUtil.validateToken(token)) { // 토큰 검증
               // 토큰 검증 실패
               setResponseFail(response);
               return false;
           }
           // ?? 토큰 검증 성공하면 HttpServletResponse 객체에 토큰에 들어있던 memberId를 넣어준다.
           // ?? 이 객체에 넣어준 memberId는 SigninArgumentResolver에서 꺼내서 사용할거다.
           request.setAttribute(Const.SIGNIN_USER, jwtUtil.getClaimFromToken(token).get("memberId", Long.class));
           return HandlerInterceptor.super.preHandle(request, response, handler);
       } else {
           setResponseFail(response);
           return false;
           }
       }


    private static void setResponseFail(HttpServletResponse response) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        // 일단 지금은 map obj 로 만듬
        Map<String, Object> map = Map.of(
                "code", 401,
                "status", "UNAUTHORIZED",
                "data", "로그인이 되어있지 않습니다."
        );

        String jsonResponse = new ObjectMapper().writeValueAsString(map); //ObjectMapper란? json 형식으로 만들어서 string으로 직렬화 역직렬화를 해주는 객체

        response.getWriter().write(jsonResponse); // io (인풋 아웃풋)를 배우면 알게됌 자바의정석 기초편 보기! getWriter = 넣어줌

    }



}

