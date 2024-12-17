package org.example.springsecurity.exam1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Retry {
    // 외부 api 통신 시 간혈적으로 튕길때가 있는데 그럴때 사용하면된다..
    // 주의할점으로는 재시도 횟수
    int value() default 3; //기본값으로 3을 지정
}
