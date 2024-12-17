package org.example.springsecurity.proxyvs;


import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.exam2.aop.CallLogAspect;
import org.example.springsecurity.exam0_aop_jwt.service.MemberService;
import org.example.springsecurity.exam0_aop_jwt.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(CallLogAspect.class)
public class ProxyCastingTest {

    /**
     *  JDK 동적 프록시는 인터페이스가 필수이고, '인터페이스'를 기반으로 프록시를 생성
     *  CGLIB는 '구체 클래스'를 기반으로 프록시를 생성
     *
     *  물론 인터페이스가 없고 구체 클래스만 있는 경우에는 CGLIB를 사용해야 한다.
     * */

    @Test
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); //false : JDK 동적 프록시


        //프록시를 인터페이스로 캐스팅 성공
        MemberService memberServiceProxy = (MemberService)
                proxyFactory.getProxy();

        log.info("proxy class = {}", memberServiceProxy.getClass());



    }
}
