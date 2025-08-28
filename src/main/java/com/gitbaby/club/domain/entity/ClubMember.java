package com.gitbaby.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "club_member")
public class ClubMember extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mno;

  private String email;
  private String password;
  private String name;
  private boolean fromSocial;

  @Builder.Default
  @ElementCollection(fetch = FetchType.EAGER)
  private Set<ClubMemberRole> roleSet = new HashSet<>();

  public void addMemberRole(ClubMemberRole role) {
    roleSet.add(role);
  }


}
