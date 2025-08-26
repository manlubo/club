  package com.gitbaby.club.security.filter;

  import com.gitbaby.club.security.dto.ClubAuthMemberDTO;
  import com.gitbaby.club.util.JWTUtil;
  import jakarta.servlet.FilterChain;
  import jakarta.servlet.ServletException;
  import jakarta.servlet.http.HttpServletRequest;
  import jakarta.servlet.http.HttpServletResponse;
  import lombok.RequiredArgsConstructor;
  import lombok.extern.log4j.Log4j2;
  import net.minidev.json.JSONObject;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.security.authentication.AuthenticationServiceException;
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
    private final JWTUtil jwtUtil;


        // public 사용 이유 = SecurityConfig에서 사용하기 위해
    public ApiLoginFilter(String defaultFilterProcessesUrl, JWTUtil jwtUtil) {
      super(defaultFilterProcessesUrl);
      this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
      log.info("===========================ApiLoginFilter");
      log.info("attemptAuthentication");
      // POST로 넘어온 것만 처리하겠다.

      if(!"POST".equalsIgnoreCase(request.getMethod())){
        throw new AuthenticationServiceException("Only POST method is supported");
      }

      String email = request.getParameter("email");
      String password = request.getParameter("pw");

      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

      return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//      log.info("===========================");
//      log.info("successfulAuthentication");
//
//      // SecurityContext 생성 / 설정
//      SecurityContext  context = SecurityContextHolder.createEmptyContext();
//      context.setAuthentication(authResult);
//      SecurityContextHolder.setContext(context);
//
//      // 세션에 저장
//      request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
//      response.sendRedirect("/");

      String mno = ((ClubAuthMemberDTO) authResult.getPrincipal()).getMno() + "";
      String token = null;

      try {
        token = jwtUtil.generateToken(mno);
        response.setContentType("text/plain");
        response.getOutputStream().write(token.getBytes());

        log.info(token);
      } catch (Exception e) {
        e.printStackTrace();
      }
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