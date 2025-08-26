package com.gitbaby.club.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
  long expire = 60 * 24 * 30; // 단위 : min (30일)

  private Key getKey() {
    String secretKeyString = "club1234567890123456789012345678901234567890"; // 32글자 이상 권장 (HS 256)
    return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
  }

  public String generateToken(String content) throws Exception {

    Claims claims = Jwts.claims()
            .subject(content)
            .issuedAt(new Date())
            .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
            .build();

    return Jwts.builder()
            .claims(claims)
            .signWith(getKey())
            .compact();
  }

  public String validateAndExtract(String tokenStr) throws Exception {
    SecretKey secretKey = Keys.hmacShaKeyFor(getKey().getEncoded());

    Claims claims = Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(tokenStr)
            .getPayload();

    return claims.getSubject();
  }

}
