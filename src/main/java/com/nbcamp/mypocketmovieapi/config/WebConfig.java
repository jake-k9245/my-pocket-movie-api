package com.nbcamp.mypocketmovieapi.config;

import com.nbcamp.mypocketmovieapi.interceptor.AuthenticationInterceptor;
import com.nbcamp.mypocketmovieapi.resolver.SigninArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final AuthenticationInterceptor authenticationInterceptor;
    private final SigninArgumentResolver signinArgumentResolver;

    public WebConfig(AuthenticationInterceptor authenticationInterceptor, SigninArgumentResolver signinArgumentResolver) {
        this.authenticationInterceptor = authenticationInterceptor;
        this.signinArgumentResolver = signinArgumentResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { // 딱봐도 기재하는 느낌임
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns(
                        "/api/**" // api 뒤로 들어오는 모든 요청 다 ~~
                )
                .excludePathPatterns(
                        "/api/members",
                        "/api/members/signin"
                );
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(signinArgumentResolver);
    }


}
