package org.example.springsecurity.exam2;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.exam2.inter.InternalService;
import org.springframework.stereotype.Component;

/**
 * 구조를 변경(분리)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    /**
     * 앞선 V1,2,2_2 방법들은 자기자신을 주입하거나 억지스러운 부분이 있었다.
     *
     * 가장 좋은 방법은 '내부 호출이 발생하지 않도록 구조를 변경하는것.'
     * */

    // callservice -> internalService
    InternalService internalService;

    public void external(){
        log.info("CallServiceV0 external");
        internalService.internal(); //외부 호출
    }

}
