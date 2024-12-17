package org.example.springsecurity.exam0_aop_jwt.service.impl;

import org.example.springsecurity.exam0_aop_jwt.anotation.ClassAop;
import org.example.springsecurity.exam0_aop_jwt.anotation.MethodAop;
import org.example.springsecurity.exam0_aop_jwt.service.MemberService;
import org.springframework.stereotype.Component;

@ClassAop
@Component
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value")
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param) {
        return "ok";
    }

}
