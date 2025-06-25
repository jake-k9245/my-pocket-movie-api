package com.nbcamp.mypocketmovieapi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //
@Retention(RetentionPolicy.RUNTIME) // 실행시점에도, 쓰여라! 반면에 override는 컴파일단계때만 쓰임
public @interface SigninMember {


}
