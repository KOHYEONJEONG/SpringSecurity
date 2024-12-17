package org.example.springsecurity.exam0_aop_jwt.service;

public interface SecurityService {

    String createToken(String subject, long ttlMillis);
    String getSubject(String token);
}
