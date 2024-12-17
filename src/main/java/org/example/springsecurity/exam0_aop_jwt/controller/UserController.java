package org.example.springsecurity.exam0_aop_jwt.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.springsecurity.exam0_aop_jwt.anotation.TokenRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    
    @TokenRequired
    @GetMapping("/user")
    public String getUser(HttpServletRequest request, HttpServletResponse response) {
            log.debug("getUser 호출");
        return "user";
    }

    @TokenRequired
    @GetMapping("/userDtl")
    public String getUserDtl(@RequestParam("userId") String  userId, HttpServletRequest request, HttpServletResponse response) {
        log.debug(userId+ " getUserDtl 호출");


        //고의적으로 ArithmeticException 에러를 만들어보겠따!
        int errorCode = 3 / 0;

        return userId;
    }
}
