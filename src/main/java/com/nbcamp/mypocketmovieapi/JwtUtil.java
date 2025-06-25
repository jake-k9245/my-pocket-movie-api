package com.nbcamp.mypocketmovieapi;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

// 로그 찍을 때
@Slf4j
// bean으로 등록해서, @postconstruct을 통해 최초에만 1번만 실행하게 해서 key를 만들어냄
@Component
public class JwtUtil { // JWT 토큰을 생성하고, 검증하고, 정보를 꺼내기 위한 헬퍼 클래스

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public final long TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 100; // 100일을 밀리초

    @Value("${jwt.secret.key}")
    private String secretKey;
    private SecretKey key;
    // 알고리즘은 하나씩 다 눌러보면 알 수 있기도 있어서, 숨기는 게 의미가 없음
    private MacAlgorithm algorithm = Jwts.SIG.HS256 ;

    @PostConstruct // 모든 주입이 끝난 뒤 자동 실행(@value 주입값이 늦게 들어올 수 있어서)
    public void init() {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }


    // 토큰 생성: 로그인 성공했을 때
    //이메일 노출되는 것보다는 ID를 넣는게 낫다고 판단했지만, nickname 중복될수 있게 설정해서 email로 다시 바꿈
    public String createToken(String email, Long memberId) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(email)
                        .claim("memberId", memberId)
                        .expiration(new Date(date.getTime() + TOKEN_EXPIRATION_TIME)) // 현재시간 + 100일후
                        .issuedAt(date)
                        .signWith(key, algorithm)
                        .compact();
    }


    // Header에서 Bearer 토큰을 가져와서 순수한 Token(bearer없는 토큰값 only)으로 반환;
    // optional로 감싸는 이유: 토큰이 없는 상태의 Null을 직접 다루지 않고, 형식이 틀릴수도 있으니 안전하게 처리
    public Optional<String> extractResolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return Optional.of(bearerToken.substring(BEARER_PREFIX.length())); //bearer 글자수만큼 앞을 잘라냄
        }
        return Optional.empty();
    }


    // 토큰 검증: 인증이 필요한 요청이 들어왔을 때
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
                    return true;
        } catch (JwtException e) {
            // 로그찍을 때, 맨위 애노테이션 @Slf4j 추가
            // == private static final Logger log = LoggerFactory.getLogger(현재 클래스명.class);
            log.warn("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }

    // 토큰에서 사용자 정보를 가져오기;
    public Claims getClaimFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

}
