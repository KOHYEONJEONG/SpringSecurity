package org.example.springsecurity.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //컨트롤러처럼 동작하며, 모든 예외를 처리할 수 있는 컨트롤러이다.
@RestController
public class GlobalExceptionHandler { //클라이언트에 가기전에 잡아줌.


    @ExceptionHandler(ArithmeticException.class)
    public Map<String, String > handlerArithmeticExceptionException(ArithmeticException e) {
        Map<String, String > map = new HashMap<>();
        map.put("errorMsg", e.getMessage());
        map.put("status", "error");
        return map;
    }


    //(중요)Exception에러는 맨 아래
    @ExceptionHandler(Exception.class)
    public Map<String, String > handlerException(Exception e) {
        Map<String, String > map = new HashMap<>();
        map.put("errorMsg", e.getMessage());
        map.put("status", "error");
        return map;
    }
}
