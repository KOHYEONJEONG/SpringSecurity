package org.example.springsecurity.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //RUNTIME: 실행시점에 읽을 수 있음/ 실행 이후에 계속 살아 있겠다.
@Target(ElementType.METHOD) //TYPE은 클래스에 붙이는 거임
public @interface TokenRequired {
}
