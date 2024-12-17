package org.example.springsecurity.exam0_aop_jwt.controller;

import lombok.RequiredArgsConstructor;
import org.example.springsecurity.exam0_aop_jwt.service.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class JwtController {

    private final SecurityService securityService;

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("security/generate/token")
    @ResponseBody
    public Map<String, Object> generateToken(@RequestParam(value="subject") String subject) {
        //subject : 식별자(주로 userId)
        String token = securityService.createToken(subject, 1000* 60*60);
        //token은 요청받을 때마다 다르게 생성해서 보냄.

        Map<String, Object> map = new HashMap<>();
        map.put("userid", subject);
        map.put("token", token);

        return map;
    }

    @GetMapping("security/get/subject")
    @ResponseBody
    public String getSubject(@RequestParam(value="token") String token) {

        String subject = securityService.getSubject(token);
        return subject;
    }



}
