package org.example.springsecurity.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.springsecurity.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * jwt는 심플하다
 * */
@Service
public class SecurityServiceImpl implements SecurityService {

    //서버는 secretKey를 가지고 있다, 암호화 key
    public static final String secretKey = "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";

    //@Override
    public String createToken(String subject, long ttlMillis) {
        //subject : 식별자(주로 userId)
        //ttlMillis : 만료기간
        if(ttlMillis == 0){
            throw new RuntimeException("토큰 만료기간은 0 이상 이어야 합니다.");
        }

        // H2256 방식으로 암호화 방식 설정
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //서명 키 생성
        byte[] apiKeySecreteByTes = secretKey.getBytes();
        Key siginigKey = new SecretKeySpec(apiKeySecreteByTes, signatureAlgorithm.getJcaName());


        // 현재 시간 설정
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //토큰 생성 *JwtBuilder 패턴 사용
        JwtBuilder builder =
                Jwts.builder()
                .setSubject(subject)        // 식별자 설정
                .setIssuedAt(now)           // 발행 시간 설정
                .setExpiration(new Date(nowMillis + ttlMillis))  // 만료 시간 설정
                .signWith(signatureAlgorithm, siginigKey);      // 서명 키 설정(siginigKey 만들어둔 걸로 서명 부분 만듬)

        return builder.compact(); //compact 함수를 통해 String으로 발행
    }


    //토근에 저장된 subject 꺼내기
   // @Override
    public String getSubject(String token) {

        byte[] apiKeySecreteByTes = secretKey.getBytes();
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecreteByTes)  //시크릿 키를 가지고 역으로 파싱하기
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();//정상적인 token이라면 subject 가져올 수 있다.
    }
}
