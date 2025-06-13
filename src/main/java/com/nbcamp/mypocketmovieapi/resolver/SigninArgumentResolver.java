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

        HttpSession session = request.getSession(false); // false 넣으면 세션이 있으면 받아오고, 없으면 null 반환함
        if(session == null || session.getAttribute(Const.SIGNIN_USER) == null) {
            throw new RuntimeException("인증 처리가 필요합니다."); //나중에 예외처리 만들어서 바꾸기
        }
        return session.getAttribute(Const.SIGNIN_USER);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SigninMember.class) // 애너테이션 확인
                && parameter.getParameterType().equals(Long.class); // 타입 확인

    }
}
