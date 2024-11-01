package org.example.springsecurity.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.springsecurity.anotation.TokenRequired;
import org.example.springsecurity.service.impl.SecurityServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.xml.bind.DatatypeConverter;
import java.util.concurrent.TimeUnit;

@Aspect
@Component //서버가 시작 시 적용됨.
public class AspectConfig {

    //befor-after 적용
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

        System.out.println("실행 완료 : "+seconds+"초 , "+ milliseconds + "ms 소요됨.");

        return result;
    }

    // @TokenRequired 가 붙은 메서드에서만 실행되는 AOP
    //실행 전에 토큰이 필요한 메서드에 붙여서 관리
    @Before("@annotation(tokenRequired)")
    public void tokenRequiredWithAnnotation(TokenRequired tokenRequired) throws Throwable {
        ServletRequestAttributes reqAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest request = reqAttributes.getRequest();

        String tokenInHeader = request.getHeader("token");
        if(StringUtils.isEmpty(tokenInHeader)){
            throw new IllegalArgumentException("Empty token");
            //GlobalExceptionHandler.class 에서 예외처리 해줌
        }
        byte[] apiKeySecreteByTes = SecurityServiceImpl.secretKey.getBytes();
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecreteByTes)  //시크릿 키를 가지고 역으로 파싱하기
                .parseClaimsJws(tokenInHeader)
                .getBody();

        if(claims == null || claims.getSubject() == null) {
            throw new IllegalArgumentException("Token Error : Claim is null");
        }
        if(!"ko".equalsIgnoreCase(claims.getSubject())) {
            throw new IllegalArgumentException("Subject doesn't match in the token");
        }
    }

}
