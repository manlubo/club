package com.gitbaby.club.security.service;

import com.gitbaby.club.security.dto.ClubAuthMemberDTO;
import com.gitbaby.club.domain.entity.ClubMember;
import com.gitbaby.club.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {
  private final ClubMemberRepository repository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<ClubMember> result = repository.findByEmailAndFromSocial(username, false);
    ClubMember member = result.orElseThrow(() -> new UsernameNotFoundException(username + " is not found"));
    log.info("loadUserByUsername: " + member.getEmail());
    log.info("======================");
    log.info("member: " + member);

    ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO (
            member.getMno(),
            member.getEmail(),
            member.getPassword(),
            member.isFromSocial(),
            member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
    );
            clubAuthMember.setName(member.getName());

            return clubAuthMember;
  }

  public ClubAuthMemberDTO getAuthDTO(Long mno) {
    ClubMember member = repository.findById(mno).orElseThrow(() -> new UsernameNotFoundException(mno + " is not found"));
    ClubAuthMemberDTO dto =  new ClubAuthMemberDTO(
            member.getMno(),
            member.getEmail(),
            member.getPassword(),
            member.isFromSocial(),
            member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toSet())
            );
    dto.setName(member.getName());

    return dto;
  }

}
