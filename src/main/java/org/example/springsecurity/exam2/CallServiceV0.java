package org.example.springsecurity.exam2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {
    //ctrl + shift + t로 테스트 만들기

    //CallServiceV0.external() 호출하면 내부에서
    // internal()이라는 자기 자신의 메서드를 호출 (this가 붙음)
    public void external(){
        log.info("CallServiceV0 external");

         //public 메서드를 내부에호출하는 경우 문제가 발생한다.
        internal(); //내부호출
    }

    //internal도 aop 대상이지만 external()  내부에 호출하기 때문에 프록시를 거치지 않는다.
    //따라서 어드바이스도 적용할 수 없다.
    public void internal() {
        log.info("CallServiceV0 internal");
    }
}
