package org.example.springsecurity.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Slf4j
@Aspect
public class TraceAspect {

    //Trace 어노테이션이 붙은 메서드에 인식됨.
    @Before("@annotation(org.example.springsecurity.exam.annotation.Trace)")
    public void beforeTrace(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs(); //넘어가는 인수정보들을 보여주기 위해서
        log.info("[trace] {} args={}", joinPoint.getSignature(), args);
    }
}
