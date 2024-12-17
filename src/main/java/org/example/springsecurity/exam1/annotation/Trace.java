package org.example.springsecurity.exam1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Trace {
    //로그출력 aop를 도와주는 어노테이션

    //로그 어노테이션 생성 사례
    // 특정 시간(1초이상, 10초이상)을 오버하는 경우 로그 남기기 등

    //한번 잘 개발해두면 전체에서 사용할 수 있다.

    //대표적으로 @Tranjactional
}
