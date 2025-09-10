package org.example.springsecurity.exam0_aop_jwt.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.springsecurity.exam0_aop_jwt.service.SecurityService;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

/**
 * jwt는 심플하다
 * */
@Service
public class SecurityServiceImpl implements SecurityService {
    /*
     * 스프링 시큐리티란?
     * - 애플리케이션 보안 = 인증과 권한을 의미한다.
     *      ㄴ 스프링 시큐리티에서 지원한다.
     * ㄴ 인증 : 출입 허가증
     * ㄴ 권한 : 요청이 모든 리소스를 다 사용할 수 있는지, 요청에 대한 권한
     * - 스프링 시큐리티는 강력한 인증과 권한 프레임워크
     * - Rest API 를 인증되고 권한이 있는 요청에만 허락해준다.
     *
     * [JWT(JSON WEB TOKEN)]
     * - 웹 표준으로 등장
     * - 보안(인증과 권한)에 많이 사용된다.
     * - JWT 토큰 형태로 사용된다.
     *    ㄴ URL-safe, 웹 브라우저 호환 , SSO 지원
     *
     * [주요 인증 방식(4가지)]
     * - 로그인 기반 인증
     * ㄴ 토큰 기반 인증
     *
     * - 인증정보를 다른 어플리케이션으로 전달
     * ㄴ 제 3자가 인증을 처리하는 방식
     * ㄴㄴ OAuth2
     * ㄴㄴ 페이스북/구글 같은 소셜 계정들을 이용하여 로그인
     *
     * - 2단계 인증
     * ㄴ Two-factor authentication
     * (1단계: 로그인, 2단계: 휴대폰 인증 또는 이메일 인증 이런거)
     * ㄴㄴ 보안 강화
     *
     * - 하드웨어 인증
     * ㄴ Hardware authentication
     * ㄴㄴ 암호화된 패스워드를 받아 복호화하여 인증을 한다던가
     *
     * [🚩세션 기반 인증 시스템 문제점]
     * - 세션
     *   ㄴ 유저가 인증할 때 이 기록을 서버에 저장
     *   ㄴ 메모리에 저장 혹은 데이터베이스 시스템 저장
     *   ㄴ 유저의 수(동시 접속)가 많으면 서버나 DB에 부하
     *
     * - 확장성
     *   ㄴ 클러스터링 구성 시 세션 정보도 같이 공유해야 함.
     *   ㄴ 서버 구성이 복잡해짐
     *
     * - CORS(Cross-Origin Resource Sharing)
     *   ㄴ 쿠키를 여러 도메인에서 관리하는 것이 번거롭다.
     *   ㄴ 세션키를 쿠키에 담기 때문에.
     * */


    //서버는 secretKey를 가지고 있다, 암호화 key
    public static final String secretKey = "4C8kum4LxyKWYLM78sKdXrzbBjDCFyfX";

    //@Override
    public String createToken(String subject, long ttlMillis) {//식별키, 만료시간
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

        //토큰 생성 *JwtBuilder 패턴 사용 (토큰 발급하는 라이브러리 사용)
        JwtBuilder builder =
                Jwts.builder()
                .setSubject(subject)                     // 식별자 설정(( 등록된 클레임 중 하나)sub 클레임을 설정)
                .claim("role", "admin")   // ✅ 커스텀 클레임 추가(Private Claim (비공개 클레임))
                .setIssuedAt(now)           // 발행 시간 설정
                .setExpiration(new Date(nowMillis + ttlMillis))  // 만료 시간 설정('세션타임아웃'이랑 비슷하지만 느낌은 다르다)
                                                                        // 서버가 알아서 로그인을 해제한다면 jwt는 자기가 티켓을 들고 다니면서 일정시간이 지나면 무효
                .signWith(signatureAlgorithm, siginigKey);      // 서명 키 설정(siginigKey 만들어둔 걸로 서명 부분 만듬)

//      [Decoded Payload]
//        {
//            "sub": "ko",
//            "role": "admin",
//            "iat": 1750063193,
//            "exp": 1750066793
//        }


        return builder.compact(); //compact 함수를 통해 String으로 발행(토큰은 단순 문자열 형태로 반환된다)
    }

   // 토큰에 저장된 subject 꺼내기
   // 파싱
   // @Override
    public String getSubject(String token) {

        byte[] apiKeySecreteByTes = secretKey.getBytes();// JWT 서명을 검증하기 위해 시크릿키를 바이트 배열로 반환
        
        Claims claims = Jwts.parser()
                .setSigningKey(apiKeySecreteByTes)  //🚨시크릿 키를 가지고 역으로 파싱하기 (클라이언트와 서버가 공유하고 있는 키), 변조됐으면 여기서 에러남.
                .parseClaimsJws(token)
                .getBody();

        //커스텀 크레임=비공개 클레임 꺼내기
        String role = claims.get("role", String.class);
        String exp = String.valueOf(claims.getExpiration());
        System.out.println("role : "+ role+", exp : "+exp);

        //등록된 클래임 꺼내기
        return claims.getSubject();//정상적인 token이라면(= 위변조가 되지 않은 토큰이라면) subject 가져올 수 있다.

//        String subject = claims.getSubject();
//        String issuer = claims.getIssuer();
//        String audience = claims.getAudience();
//        Date expiration = claims.getExpiration();
//        Date issuedAt = claims.getIssuedAt();
//        String jwtId = claims.getId();
    }
}
