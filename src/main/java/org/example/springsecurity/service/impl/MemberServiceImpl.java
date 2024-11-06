package org.example.springsecurity.service.impl;

import org.example.springsecurity.anotation.ClassAop;
import org.example.springsecurity.anotation.MethodAop;
import org.example.springsecurity.service.MemberService;
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
