package org.example.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class PointCutTests {

    //AspectJExpressionPointcut 최 상단에 Pointcut이 존재함.
    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod; //테스트가 되고 return값을 넣어줌.

    @BeforeEach //JUnit만 있음
    public void init() throws NoSuchMethodException {
        //hello는 메소드명, String은 파라미터 자료형
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod(){
        //excution에 넣을 표현식 구조를 짜기전에 보기 위해서~
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void exactMetch(){
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String org.example.springsecurity.service.impl.MemberServiceImpl.hello(String))");
                                                  //String 같은 경우 앞에 java.lang 제외해도 됨.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void allMathch(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }





}
