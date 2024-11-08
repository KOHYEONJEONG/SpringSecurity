package org.example.springsecurity.exam2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

    /**
     * 스프링 빈을 지연해서 조회하면 되는데
     * 이때 ObjectProvider와 ApplicationContext를 사용하면된다.
     *
     *
     * 단, ApplicationContext는 너무 많은 기능을 제공해서 비추
     * ObjectProvider를 살펴보자(CallServiceV2_2.class)
     *
     * */

    private final ApplicationContext applicationContext;

    public void external(){
        log.info("CallServiceV2 external");
        //ctrl+shift+v : 변수명 생성
        CallServiceV2 bean = applicationContext.getBean(CallServiceV2.class);
        bean.internal(); //외부 호출로 인해 internal()도 aop 적용을 받을 수 있다.
    }

    //private로 하니까 안되네?
    // -> aop는 private 메서드처럼 작은 단위에는 적용ㄷ하지 않는다. (public만 적용)
    public void internal() {
        log.info("CallServiceV2 internal");
    }
}
