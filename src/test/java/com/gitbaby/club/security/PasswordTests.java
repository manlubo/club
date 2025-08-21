package com.gitbaby.club.security;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class PasswordTests {
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("인코더 테스트")
  public void testEncode() {
    String password = "1234";
    String enPw = passwordEncoder.encode(password);
    log.info(enPw);
    log.info(passwordEncoder.matches(password, enPw));
  }
}
