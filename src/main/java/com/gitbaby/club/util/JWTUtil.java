package com.gitbaby.club.util;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

  private Key getKey() {
    String secretKey = "club12345678";
    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String content) throws Exception {
    // 단위 : min (30일)
    long expire = 60 * 24 * 30;

    return Jwts.builder()
            .issuedAt(new Date())
            .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
            .claim("sub", content)
            .signWith(getKey())
            .compact();
  }

  public String validateAndExtract(String tokenStr) throws Exception {
    String contentValue = null;
    return null;
  }

}
