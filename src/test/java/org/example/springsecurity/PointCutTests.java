package org.example.springsecurity;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.*;

/**
 *   ex)
 *   hello.aop.member.*(1).*(2)
 *   - (1) : 타입
 *   - (2) : 메서드 이름
 *
 *
 *  . 와 .. 차이
 *  -  . : 정확하게 해당 위치의 패키지
 *  - .. : 해당 위치의 패키지와 그 하위 패키지도 포함
 *
 *
 * */
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

    //@Test
    void printMethod(){
        //excution에 넣을 표현식 구조를 짜기전에 보기 위해서~
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod={}", helloMethod);
    }

    @Test
    void packageExactMatchFalse(){
        pointcut.setExpression("execution(* org.example.springsecurity.*.*(..)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse(); //springsecurity 패키지의 정확하게 해당 패키지 (하위 패키지 NO)
    }

    @Test
    void packageExactMetch1(){
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String org.example.springsecurity.service.impl.MemberServiceImpl.hello(String))");
                                                  //String 같은 경우 앞에 java.lang 제외해도 됨.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMetch2(){
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String org.example.springsecurity.service.impl.*.*(..))");
        //org.example.springsecurity.service.impl 패키지 안에 있는건 다 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMetch3(){
        //public java.lang.String org.example.springsecurity.service.impl.MemberServiceImpl.hello(java.lang.String)
        pointcut.setExpression("execution(public String org.example.springsecurity.*.*(..))");
        //.*(1).*(2) : (1) 타입, (2) 메서드 이름
        //org.example.springsecurity.service.impl 정확하게 해당 패키지 (하위 패키지 NO).
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 타입 매칭 (이라 부름)
     * */
    @Test
    void typeMatchSuperType(){
        //MemberService 처럼 부모타입을 선언해도 그 자식 타입은 매칭된다.
        //다형성에서 부모타입 = 자식타입 이 할당 가능하다는 점을 떠올려보면 된다.
        //부모는 자식을 품을 수 있지만, 자식은 부모를 품을 수 없지
        pointcut.setExpression("execution(* org.example.springsecurity.service.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchNoSuperTypeMethodFalse() throws NoSuchMethodException {

        //부모타입에서 선언된 메서드만 가능하다
        // hello 메서드 밖에 없음.
        // MemberServiceImpl클래스의 internal 메서드 내부에서 선언된 메서드이다.
        pointcut.setExpression("execution(* org.example.springsecurity.service.MemberService.*(..))");
        Method internalMethod = MemberServiceImpl.class.getMethod("internal",String.class);
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isTrue();

    }
    //가장 많이 생략한 포인트 컷
    @Test
    void allMathch(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    /**
     * ## 파라미터로 구분하는 방법 ##
     * argsMethod : 있는 버전
     * noArgsMethod2 : 없는 버전
     * argsMethodStar3 : 파라미터 수로 지정하는 버전
     * argsMethodAll4 : 수자와 무관하게 모든 타입을 허용하는 ex) (), (xxx), (xxx,xxx ..)
     * argsMethodAll5 : 시작하는 파라미터 자료형 고정하고 그 뒤 모든 타입 허용 ex) (String),(String, .. 등)
     * **/ 
    @Test
    void argsMethod(){
        pointcut.setExpression("execution(* *(String))");
        //helloMethod는 hello(String) 이므로 true이다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void noArgsMethod2(){
        pointcut.setExpression("execution(* *())");
        //helloMethod는 hello(String) 이므로 false이다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }


    @Test
    void argsMethodStar3(){
        pointcut.setExpression("execution(* *(*))");
        //helloMethod는 hello(String) 이므로 false이다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMethodAll4(){
        pointcut.setExpression("execution(* *(..))");
        //helloMethod는 hello(String) 이므로 false이다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void argsMethodAll5(){
        pointcut.setExpression("execution(* *(String, ..))");
        //helloMethod는 hello(String) 이므로 false이다.
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }



}
