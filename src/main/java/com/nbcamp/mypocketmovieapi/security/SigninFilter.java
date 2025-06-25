package com.nbcamp.mypocketmovieapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.mypocketmovieapi.JwtUtil;
import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.common.CommonResponse;
import com.nbcamp.mypocketmovieapi.dto.member.SignInRequestDto;
import com.nbcamp.mypocketmovieapi.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.PrintWriter;

// 로그인 인증 후 토큰 발급하는 Filter
public class SigninFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public SigninFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/members/signin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //InputStream 안에 받아온 정보들이 들어있음, 예외처리해줘야함
        try {
            SignInRequestDto signInRequestDto = objectMapper.readValue(request.getInputStream(), SignInRequestDto.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword());
            return getAuthenticationManager().authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // attemptAuthentication() <- 인증이 성공되면 실행되는 메서드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetailsImpl principal = (UserDetailsImpl) authResult.getPrincipal(); // principal에 정보들이 담겨있음, object여서 캐스팅해줌

        // 사용자 정보, email, id
        Member member = principal.getMember();

        // 사용자 정보로 토큰 생성
        String token = jwtUtil.createToken(member.getEmail(), member.getId());

        // 응답 (HttpServletResponse) 헤더에 토큰을 추가
        // -H Authorization=토큰
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 공통 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8"); // 한글과 같은 유니코드 문자가 깨지지 않으려면 이 옵션을 넣어줘야함.
        CommonResponse<Void> commonResponse = CommonResponse.success(CommonCode.SUCCESS_SIGNIN);

        // io가 오류가 많이 나니, try catch 를 되도록 해주면 좋다
        try {
            // 객체를 JSON 문자열로 변환
            String jsonResponse = objectMapper.writeValueAsString(commonResponse);

            // 응답 본문에 JSON 데이터를 write
            PrintWriter out = response.getWriter();
            out.print(jsonResponse); // println
            out.flush(); // 버퍼에 남아있던 데이터들을 클라이언트에게 출력해줌
            // == response.getWriter().write(jsonResponse); 위 3줄과 똑같은 기능인데 이렇게도 할 수 있음

        } catch (Exception ex) {
            // JSON 변환 중에 오류가 발생하니까 이걸 try-catch로 잡아서 에러 응답을 가공해준다.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            CommonResponse<Void> errorResponse = CommonResponse.fail(CommonCode.FAIL_SERVER);
            String errorJsonResponse = objectMapper.writeValueAsString(errorResponse);
            response.getWriter().write(errorJsonResponse);
        }

    }

    // attemptAuthentication() <- 인증을 실패하면 실행되는 메서드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        CommonResponse<Void> errorResponse = CommonResponse.fail(CommonCode.FAIL_SIGNIN_INVALID_PASSWORD);
        String errorJsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(errorJsonResponse);
    }
}
