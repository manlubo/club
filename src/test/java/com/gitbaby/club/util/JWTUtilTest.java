package com.gitbaby.club.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class JWTUtilTest {
  private JWTUtil jwtUtil;

  @BeforeEach // 테스트 하기전 먼저 실행되는 메서드
  public void testBefore() {
    jwtUtil = new JWTUtil();
  }


  @Test
  @DisplayName("인코딩 테스트")
  public void testEncoder() throws Exception {
    String email="user99@gmail.com";

    String str = jwtUtil.generateToken(email);
    log.info(str);

    String decode = jwtUtil.validateAndExtract(str);
    log.info(decode);
  }

}
