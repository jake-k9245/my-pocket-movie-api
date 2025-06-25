package com.nbcamp.mypocketmovieapi.security;

import com.nbcamp.mypocketmovieapi.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

// jwt 검증 필터 | OncePerRequestFilter

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImplImpl userDetailsServiceImpl;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImplImpl userDetailsServiceImpl) {
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    // 필터들은 다 연계되어 계속 진행되는데,
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> tokenOpt = jwtUtil.extractResolveToken(req);

        if (tokenOpt.isPresent()) {
           String token = tokenOpt.get();

           if (!jwtUtil.validateToken(token)) {
               // 토큰 검증 실피, 요청 종료
              return;
           }

           // Token에서 생성할 때 넣어준 email을 꺼내온다.
            String email = jwtUtil.getClaimFromToken(token).getSubject();
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);

            // Security에서 활용하는 인증 토큰
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 인증 토큰을 저장하는 저장소 (Context)
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context); // 인증 완료, 여기에 인증 토큰을 넣어주면 Security에서 인증된 요청으로 처리함.
        }

        // 다음 필터로 넘어감
        filterChain.doFilter(req, res);
    }

}