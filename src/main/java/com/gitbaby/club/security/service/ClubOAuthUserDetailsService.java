package com.gitbaby.club.security.service;

import com.gitbaby.club.security.dto.ClubAuthMemberDTO;
import com.gitbaby.club.domain.entity.ClubMember;
import com.gitbaby.club.domain.entity.ClubMemberRole;
import com.gitbaby.club.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuthUserDetailsService extends DefaultOAuth2UserService {
  private final ClubMemberRepository repository;
  private final PasswordEncoder passwordEncoder;


  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
    log.info("userRequest:" + userRequest);
    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName:" + clientName);
    log.info(userRequest.getAdditionalParameters());
    OAuth2User oAuth2User = super.loadUser(userRequest);

    oAuth2User.getAttributes().forEach((k, v) -> {
      log.info(k + ":" + v);
    });

    String email = null;

    if(clientName.equals("Google")) {
      email = oAuth2User.getAttribute("email");
    }

    log.info("email:" + email);

    ClubMember member = saveSocialMember(email);
    List<GrantedAuthority> authorities = member.getRoleSet().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());

    return new ClubAuthMemberDTO(
            member.getMno(),
            member.getEmail(),
            member.getPassword() != null ? member.getPassword() : "1111",
            member.isFromSocial(),
            authorities,
            oAuth2User.getAttributes()
    );
  }

  private ClubMember saveSocialMember(String email) {
    Optional<ClubMember> result = repository.findByEmailAndFromSocial(email, true);

    if(result.isPresent()) {
      return result.get();
    }

    ClubMember clubMember = ClubMember.builder()
            .email(email)
            .name(email)
            .password(passwordEncoder.encode("1111"))
            .fromSocial(true)
            .build();

    clubMember.addMemberRole(ClubMemberRole.USER);
    repository.save(clubMember);

    return clubMember;
  }
}
