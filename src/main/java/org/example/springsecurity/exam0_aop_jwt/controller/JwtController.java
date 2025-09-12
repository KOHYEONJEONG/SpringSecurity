package org.example.springsecurity.exam0_aop_jwt.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    /*
    * [토큰 기반 인증 시스템 작동 원리]
    * - 유저가 아이디와 비밀번호로 로그인 수행
    * - 서버측에서 해당정보 검증
    * - 계정정보가 정확하다면 서버측에서 유저에서 signed 토큰 발급
    * - 클라이언트에서 토큰(긴 문자열 형태)을 저장(웹 브라우저)해 두고 요청마다 토큰을 서버에 함께 전달
    * - 서버에서 토큰을 검증하고 요청에 응답
    *
    *   (🚨발급받은 토큰은 요청 시 HTTP 헤더에 포함시켜서 전달한다.)
    *
    *   해커가 토큰을 볼 수 있다. (중요한 정보는 담지 말자)
    *   다만 토큰을 위조하기는 쉽지 않다.
    *   또 토큰은 만료기간을 정할 수 있다.(효율적으로 사용 가능하다)
    *
    * [토큰 장점]
    *   무상태이며 확정성이 있다
    *   ㄴ 확장: 토큰 바디 부분에 여러 데이터를 담을 수 있다.
    *   보안성( 쿠키 사용하지 않음, 토큰 환경에서도 취약점은 존재한다)
    *   여러 플랫폼 및 도메인(CORS - 아무 도메인이나 토큰만 유효하면 요청이 정상적으로 처리된다.)
    *   웹 표준(인증 시 토큰을 사용한다. 토큰 포맷으로 JWT는 공식 표준이다.)
    *
    * [JWT 특징]
    *   웹 표준으로 다양한 프로그래밍 언어 지원한다.
    *   Self-contained : 필요한  모든 정보를 자체적으로 가지고 있다.
    *   웹 서버의 경우 http 헤더에 넣어서 전달 또는 URL 파라미터로 전달 가능(🚨단순 문자열이기 때문에 URL로 전달 가능하다)
    *
    * [JWT 사용되는 상황]
    *   회원인증
    *   정보 교환
    *
    * [JWT 구조]
    *  3개 점을 기준으로 나뉨
    * ⭐형식 : 헤더(HEADER).내용(PAYLOAD).서명(SIGNATURE)
    * ㄴ 서명 부분을 통해 변조됐는지 알 수 있다.
    *
    * [헤더 구조]
    *  2가지 정보를 포함
    *  - typ : 토큰의 타입을 지정(ex: "JWT")
    *  - alg : 해싱 알고르즘 지정
    *     {
    *           "typ" : "JWT"
    *           "alf" : "HS256"
    *     }
    *
    *  [페이로드 구조]
    *   토큰을 담을 정보가 포함(클레임(Claim)이라고 한다. key:value 쌍으로 구성
    *   🚨클레임은 세분류로 나뉜다. (문서 : https://www.iana.org/assignments/jwt/jwt.xhtml)
    *     - 등록된(registered) 클레임 : iss, sub, aud, exp, nbf, sat, jti (키워드가 이미 정의됨)
    *     - 공개(public) 클레임 : 충돌 방지 이름 필요(주로 URI 형식으로 네이밍)
    *     - ⭐비공개(private) 클레임 : 클라이언트 간의 현의 하에 사용되는 클레임 이름들 (주로 필요한 정보를 담는다)
    *
    *   [서명 구조]
    *   헤더의 인코딩 값과 정보의 인코딩 값을 합친 후 주어진 비밀키로 해쉬하여 생성
    *   ㄴ https://jwt.io/
    *   ㄴ jwt 형태: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTUxNjIzOTAyMn0.KMUFsIDTnFmyG3nMiGM6H9FNFUROf3wh7SmqJp-QV30
    *
    * */

    private final SecurityService securityService;

    @GetMapping("/")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    /*설명
       : 클라이언트로부터 요청이 들어오면 토큰을 응답한다.

       http://localhost:8070/security/generate/token?subject=ko

       여기서 ko가 userId 같은거다.
     */
    @GetMapping("security/generate/token")
    @ResponseBody
    public Map<String, Object> generateToken(@RequestParam(value="userId") String userId, HttpServletResponse response) {
        //subject : 식별자(주로 userId)
        String token = securityService.createToken(userId, 1000* 60*60);// 인자 : 사용자정보 , 토큰 만료 시간 ( 1000 : 1초 , 1000 * 60 : 1분)
        //token은 요청받을 때마다 다르게 생성해서 보냄.

        Map<String, Object> map = new HashMap<>();
        map.put("userid", userId);
        map.put("token", token);

        //브라우저 > Application > Cookies > ACCESS_TOKEN
        Cookie accessCookie = new Cookie("ACCESS_TOKEN", token);
        accessCookie.setHttpOnly(true);
//        accessCookie.setSecure(true);                  // HTTPS 권장
        accessCookie.setPath("/");
        accessCookie.setMaxAge(1 * 60);               // 15분
        accessCookie.setAttribute("SameSite", "Lax");  // 톰캣9+ 또는 서블릿 6 미만이면 setHeader로 수동
        response.addCookie(accessCookie); //쿠키 생성

        return map;
    }

    /**
     * 발급 받은 토큰으로 이후부터 달고 들어와야한다.(방법 : 파라미터 또는 HEADER)
     *
     * 예시1 ) 아래는 파라미터에 발급받은 토큰을 넣어서 요청
     * http://localhost:8070/security/get/subject?token=발행된 토큰
     *
     * 추가로
     * https://www.jwt.io/에서 발급받은 토큰을 'ENCODED VALUE'에 입력하면
     * HEDAER와 PAYLOAD가 보여진다.
     * */
    @GetMapping("security/get/subject")
    @ResponseBody
    public String getSubject(@RequestParam(value="token") String token) {

        String subject = securityService.getSubject(token);
        return subject;
    }



}
