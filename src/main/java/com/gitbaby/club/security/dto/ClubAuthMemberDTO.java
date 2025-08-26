package com.gitbaby.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {
  private Long mno;
  private String email;
  private String name;
  private String password;
  private boolean fromSocial;
  private Map<String, Object> attributes;

  public ClubAuthMemberDTO(
          Long mno,
          String username,
          String password,
          boolean fromSocial,
          Collection<? extends GrantedAuthority> authorities,
          Map<String, Object> attr) {
    this(mno, username, password, fromSocial, authorities);
    this.attributes  = attr;
  }

  public ClubAuthMemberDTO(Long mno, String username, String password, boolean fromSocial, Collection<? extends GrantedAuthority> authorities) {
    super(username, password, authorities);
    this.mno = mno;
    this.email = username;
    this.password = password;
    this.fromSocial = fromSocial;
  }

  @Override
  public String getName() {
    return attributes == null ? name : (String) attributes.get("email");
  }

}
