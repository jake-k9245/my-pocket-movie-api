package com.nbcamp.mypocketmovieapi.resolver;

import com.nbcamp.mypocketmovieapi.common.Const;
import com.nbcamp.mypocketmovieapi.common.SigninMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class SigninArgumentResolver implements HandlerMethodArgumentResolver { //시큐리티 안쓰니까 하고있는거임


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        // AuthenticationInterceptor에서 넣어준 memberId를 꺼내온다.
        Object memberIdObj = request.getAttribute(Const.SIGNIN_USER);

        if(memberIdObj == null) {
            throw new IllegalAccessException("Jwt 정보에 유요한 memberId가 존재하지 않습니다.");
        }

        return memberIdObj;
    }

    // 애너테이션 + 타입 둘다 있으면 true로, 이 클래스를 실행시킴
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SigninMember.class) // 애너테이션 확인
                && parameter.getParameterType().equals(Long.class); // 타입 확인

    }
}
