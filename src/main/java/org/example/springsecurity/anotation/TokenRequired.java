package org.example.springsecurity.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//아래 2개 어노테이션과 @interface는 고정값으로 생각하면된다.
@Retention(RetentionPolicy.RUNTIME) //RUNTIME: 실행시점에 읽을 수 있음/ 실행 이후에 계속 살아 있겠다.
@Target(ElementType.METHOD) //METHOD는 메소드에 붙이는 거임
public @interface TokenRequired {
}
