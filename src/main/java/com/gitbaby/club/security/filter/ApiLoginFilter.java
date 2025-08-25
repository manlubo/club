  package com.gitbaby.club.security.filter;

  import jakarta.servlet.FilterChain;
  import jakarta.servlet.ServletException;
  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;
  import lombok.extern.log4j.Log4j2;
  import net.minidev.json.JSONObject;
  import org.springframework.security.authentication.BadCredentialsException;
  import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
  import org.springframework.security.core.Authentication;
  import org.springframework.security.core.AuthenticationException;
  import org.springframework.security.core.context.SecurityContext;
  import org.springframework.security.core.context.SecurityContextHolder;
  import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
  import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

  import java.io.IOException;
  import java.io.PrintWriter;

  @Log4j2
  public class ApiLoginFilter extends AbstractAuthenticationProcessingFilter {

    // public 사용 이유 = SecurityConfig에서 사용하기 위해
    public ApiLoginFilter(String defaultFilterProcessesUrl) {
      super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
      log.info("===========================ApiLoginFilter");
      log.info("attemptAuthentication");

      String email = request.getParameter("email");
      String password = request.getParameter("pw");

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

      return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
      log.info("===========================");
      log.info("successfulAuthentication");

      // SecurityContext 생성 / 설정
      SecurityContext  context = SecurityContextHolder.createEmptyContext();
      context.setAuthentication(authResult);
      SecurityContextHolder.setContext(context);

      // 세션에 저장
      request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
      response.sendRedirect("/");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
      log.info("==========================");
      log.info("unsuccessfulAuthentication");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      JSONObject json = new JSONObject();
      String message = failed.getMessage();
      json.put("message", message);
      json.put("code", "401");

      PrintWriter out = response.getWriter();
      out.print(json);
    }
  }