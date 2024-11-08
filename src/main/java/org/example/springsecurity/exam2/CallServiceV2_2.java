package org.example.springsecurity.exam2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2_2 {

    /**
     * 스프링 빈을 지연해서 조회하면 되는데
     * 이때 ObjectProvider와 ApplicationContext를 사용하면된다.
     *
     * 객체를 스프링 컨테이너에서 조회하는 것을 스프링 빈 생성 시점이 아니라
     * 실제 객체를 사용하는 시점으로 지연할 수 있다.
     *
     *
     * 하지만 아직도 어색하다...
     * */

    //ObjectProvider는 실제 사용시점에 지여할
    private final ObjectProvider<CallServiceV2_2> v2ObjectProvider;

    public void external(){
        log.info("CallServiceV2_2 external");

        CallServiceV2_2 bean = v2ObjectProvider.getObject();
        bean.internal(); //외부 호출로 인해 internal()도 aop 적용을 받을 수 있다.
    }

    //private로 하니까 안되네?
    public void internal() {
        log.info("CallServiceV2_2 internal");
    }
}
