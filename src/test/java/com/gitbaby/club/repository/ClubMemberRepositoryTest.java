package com.gitbaby.club.repository;

import com.gitbaby.club.domain.entity.ClubMember;
import com.gitbaby.club.domain.entity.ClubMemberRole;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class ClubMemberRepositoryTest {
  @Autowired
  private ClubMemberRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("더미 회원 생성")
  public void insert() {
    IntStream.rangeClosed(1, 100).forEach(i -> {
      ClubMember member = ClubMember.builder()
              .email("user" + i + "@gmail.com")
              .name("사용자" + i)
              .fromSocial(false)
              .password(passwordEncoder.encode("1111"))
              .build();
      member.addMemberRole(ClubMemberRole.USER);
      if(i > 80){
        member.addMemberRole(ClubMemberRole.MANAGER);
      }
      if (i > 90){
        member.addMemberRole(ClubMemberRole.ADMIN);
      }

      repository.save(member);
    });
  }


  @Test
  @DisplayName("회원 조회")
  public void testFindByEmail() {
    Optional<ClubMember> result = repository.findByEmail("user99@gmail.com", false);
    ClubMember member = result.orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾지 못했습니다."));
    log.info(member);
  }
}
