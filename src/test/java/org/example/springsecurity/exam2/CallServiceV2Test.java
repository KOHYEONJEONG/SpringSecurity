package org.example.springsecurity.exam2;

import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.exam2.aop.CallLogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callServiceV2;

    //CallServiceV1Test랑 같은 내용이 출력됨

    @Test
    void external() {
        callServiceV2.external();
    }


}