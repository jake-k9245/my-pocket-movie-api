package com.nbcamp.mypocketmovieapi.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //
@Retention(RetentionPolicy.RUNTIME) // 실행할 때, 쓰여라! override 는 컴파일단계때 쓰임
public @interface SigninMember {


}
