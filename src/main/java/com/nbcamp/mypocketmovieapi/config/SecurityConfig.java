package com.nbcamp.mypocketmovieapi.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbcamp.mypocketmovieapi.JwtUtil;
import com.nbcamp.mypocketmovieapi.security.JwtAuthenticationFilter;
import com.nbcamp.mypocketmovieapi.security.SigninFilter;
import com.nbcamp.mypocketmovieapi.security.UserDetailsServiceImplImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper; //(스프링 제공)
    private final UserDetailsServiceImplImpl userDetailsServiceImpl;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // JWT 검증 및 인증 Filter
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtil, userDetailsServiceImpl);
        return jwtAuthenticationFilter;
    }

    // 로그인 및 토큰 발급 Filter
    @Bean
    public SigninFilter signinFilter() throws Exception {
        SigninFilter signinFilter = new SigninFilter(jwtUtil, objectMapper);
        signinFilter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return signinFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화 (JWT 방식에서는 필요없음)
        http.csrf(AbstractHttpConfigurer::disable);

        // Security에서 제공하는 폼 로그인 사용하지 않음
        http.formLogin(AbstractHttpConfigurer::disable);

        // JWT 기반으로 인증 처리하기 때문에 기본 설정인 session 방식을 비활성화
        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // 사용자 Request URL 제어
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll() // .permitAll() 앞에것들은 다 승인을 해줘라!
                .requestMatchers("/api/members", "/", "/api/members/signin", "/actuator/health").permitAll()
                .anyRequest().authenticated()
        );



        // Security에서 관리하고 순서대로 동작하는 Security Filter Chain에 우리가 만든 JWT를 검증하고 인증 처리하는 Filter를 추가한다.
        // 첫번째는 일반 필터 가능하지만, 두번째는 무조건 스프링 필터여야함
        http.addFilterBefore(jwtAuthenticationFilter(), SigninFilter.class);

        // 로그인 Filter
        http.addFilterBefore(signinFilter(), UsernamePasswordAuthenticationFilter.class);

        // JwtAuthenticationFilter -> SigninFilter -> UsernamePasswordAuthenticationFilter
        // 필터 순서: 사용자 요청 -> JWT 검증 Filter -> Signin Filter (/api/members/signin) -> UsernamePasswordAuthenticationFilter

        return http.build();
    }

}
