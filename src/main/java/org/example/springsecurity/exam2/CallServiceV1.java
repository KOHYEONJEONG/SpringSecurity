package org.example.springsecurity.exam2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    //자기자신
    private CallServiceV1 callServiceV1;

    //생성자로 자신을 의존관계 주입하는건 비추한다. 아니 오류나서 안된다
    //내 자신을 의존관계 주입하려면 setXXX로 하면된다.


    //자기 자신 주입은 SETTER를 추천한다.
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external(){
        log.info("CallServiceV1 external");
        callServiceV1.internal(); //외부 호출로 인해 internal()도 aop 적용을 받을 수 있다.
    }

    //private로 하니까 안되네?
    public void internal() {
        log.info("CallServiceV1 internal");
    }
}
