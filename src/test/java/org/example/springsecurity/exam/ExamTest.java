package org.example.springsecurity.exam;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.exam.aop.RetryAspect;
import org.example.springsecurity.exam.aop.TraceAspect;
import org.example.springsecurity.exam.service.ExamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({TraceAspect.class, RetryAspect.class})
@Slf4j
@SpringBootTest
public class ExamTest {

    @Autowired
    ExamService examService;

    @Test
    void test(){
        for(int i=0;i<10;i++){

            log.info("client request i={}",i);
            examService.request("data : "+i);

            //실행해보면 테스트가 5번째 루프를 실행할 때 리포지토리 위치에서 예외가 발생하면서 실패하는 것을 확인할 수 있다.
        }
    }
}
