package com.gitbaby.club.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

  private AntPathMatcher antPathMatcher; // spring 경로 패턴 체크 클래스
  private String pattern;

  public ApiCheckFilter(String pattern) {
    this.antPathMatcher = new AntPathMatcher();
    this.pattern = pattern;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("RequestUri: " + request.getRequestURI());
    log.info(antPathMatcher.match(pattern, request.getRequestURI()));

    if (antPathMatcher.match(pattern, request.getRequestURI())) {
      log.info("============================ApiCheckFilter");
      log.info("============================ApiCheckFilter");
      log.info("============================ApiCheckFilter");

      if (checkAuthHeader(request)) {
        filterChain.doFilter(request, response);
        return;
      }
      else {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        String message = "Fail Check Api Token";
        json.put("code", "403");
        json.put("message", message);

        response.getWriter().print(json);
        return;
      }
    }

    filterChain.doFilter(request, response);
  } // end of doFilterInternal

  private boolean checkAuthHeader(HttpServletRequest request) {
    boolean checkResult = false;
    String authHeader = request.getHeader("Authorization");
    if (StringUtils.hasText(authHeader)) {
      log.info("Authorization exist: {}", authHeader);

      if(authHeader.equals("12345678")){
        checkResult = true;
      }

    }


    return checkResult;
  }
}
