package com.gitbaby.club.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Note extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long num;

  @Setter
  private String title;

  @Setter
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private ClubMember writer;


}
