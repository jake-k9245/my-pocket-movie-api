package com.nbcamp.mypocketmovieapi.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.mypocketmovieapi.common.Const;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.Serializable;
import java.util.Map;

@Component // bean으로 등록해줌
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // false 넣으면 세션이 있으면 받아오고, 없으면 null 반환함

        if(session == null || session.getAttribute(Const.SIGNIN_USER) == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8"); // 응답할 때 어떤 타입으로 응답하는지 설정

            // 일단 지금은 map obj 로 만듬
            Map<String, Object> map = Map.of(
                    "code", 401,
                    "status", "UNAUTHORIZED",
                    "data", "로그인이 되어있지 않습니다."
            );

            String jsonResponse = new ObjectMapper().writeValueAsString(map); //ObjectMapper란? json 형식으로 만들어서 string으로 직렬화 역직렬화를 해주는 객체

            response.getWriter().write(jsonResponse); // io (인풋 아웃풋)를 배우면 알게됌 자바의정석 기초편 보기! getWriter = 넣어줌

            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }



}

