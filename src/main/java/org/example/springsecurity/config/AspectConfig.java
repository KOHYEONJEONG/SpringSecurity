package org.example.springsecurity.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class AspectConfig {

    @Around("execution(* org.example.springsecurity.controller.HomeController.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("실행 시작: "
        + joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis() - startTime;

        // 밀리초를 초 단위로 변환
        long seconds = TimeUnit.MILLISECONDS.toSeconds(endTime);
        long milliseconds = endTime % 1000;

        System.out.println("실행 완료 : "+seconds+"초 , "+ milliseconds + "ms 소요");

        return result;
    }
}
