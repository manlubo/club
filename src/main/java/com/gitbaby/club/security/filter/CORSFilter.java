package com.gitbaby.club.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    // 허용하고자 하는 주소(url)를 설정, "*" : 모든 주소를 허용, 2번째 인자에 스트링 한개의 값만 허용
//    List<String> allowOrigin = Arrays.asList("http://localhost:8080", "http://localhost:3000", "http://localhost:4000");
//    String origin = request.getHeader("Origin");
//    if (allowOrigin.contains(origin)) {
//      response.setHeader("Access-Control-Allow-Origin", origin);
//    }
//    ^ 여러개의 값 쓰는법 ^

    response.setHeader("Access-Control-Allow-Credentials", "true"); // 쿠키 or 토큰 사용 여부
    response.setHeader("Access-Control-Allow-Methods", "*"); // 전체 메서드 허용 (GET, POST 등)
    response.setHeader("Access-Control-Max-Age", "3600"); // 초단위 지속시간
    // preflight :
    // OPTIONS로 먼저 데이터를 전송해 사전확인 작업
    // OPTIONS /api/data HTTP/1.1
    // Origin: http://localhost:3000
    // Content-Type: application/json
    // Authorization: Bearer ~~~~

    response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
    // Origin : 요청주소, 포트
    // X-Requested-With : 비동기 요청시 만들어짐 (XMLhttpRequest)
    // Content-Type : Body의 형식
    // Accept : 응답 받을 때의 데이터 형식 (application/json, text/html, text/plain 등)
    // Key : 사용자 커스텀 헤더
    // Authorization : 인증정보

    if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      filterChain.doFilter(request, response);
    }

  }

}
