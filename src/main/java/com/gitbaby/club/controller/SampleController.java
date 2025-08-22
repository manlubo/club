package com.gitbaby.club.controller;

import com.gitbaby.club.domain.dto.ClubAuthMemberDTO;
import com.gitbaby.club.domain.entity.ClubMember;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
public class SampleController {

  @PreAuthorize("permitAll()")
  @GetMapping("sample/all")
  public void sample() {
    log.info("sample");
  }

  @PreAuthorize("hasRole('USER')")
  @GetMapping ("sample/member")
  public void exMember(@AuthenticationPrincipal ClubAuthMemberDTO member) {
    log.info("exMember: " + member);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("sample/admin")
  public void exAdmin(){
    log.info("exAdmin");
  }

  @PreAuthorize("#member != null && #member.username eq \"user99@gmail.com\"")
  @GetMapping("sample/only99")
  public String only(@AuthenticationPrincipal ClubAuthMemberDTO member) {
    log.info("only99");
    log.info("member: " + member);
    return "sample/only99";
  }

  @PreAuthorize("hasRole('USER') && #member != null && #member.fromSocial")
  @GetMapping("member/modify")
  public String modify(@AuthenticationPrincipal ClubAuthMemberDTO member) {
    return "sample/modify";
  }
}
