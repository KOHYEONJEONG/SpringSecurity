package org.example.springsecurity.exam1.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.springsecurity.exam1.annotation.Retry;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {

        log.info("[retry] {} , retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value(); //3
        Exception exceptionHolder = null; //예외를 담아두기 위해


        for (int retryCount = 1; retryCount <= maxRetry; retryCount++) {
            try {
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                /**
                 * [retry] try count=1/3
                 * [retry] try count=2/3  <-- 재시도 2번만에 다시 실행됐다라고 이해하면 된다
                  * */
                
                return joinPoint.proceed();
            }
            catch (Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
