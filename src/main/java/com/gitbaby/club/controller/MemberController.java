package com.gitbaby.club.controller;

import com.gitbaby.club.security.service.ClubUserDetailsService;
import com.gitbaby.club.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class MemberController {
  private final ClubUserDetailsService service;
  private final JWTUtil jwtUtil;

  @GetMapping("{token}")
  public ResponseEntity<?> getMember(@PathVariable String token) throws Exception {

    return ResponseEntity.ok(service.getAuthDTO(Long.valueOf(jwtUtil.validateAndExtract(token))));
  }
}
