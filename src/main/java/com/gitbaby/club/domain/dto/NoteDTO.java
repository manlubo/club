package com.gitbaby.club.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
  private Long num;
  private String title;
  private String content;
  private Long writerMno;
  private LocalDateTime regDate;
  private LocalDateTime modDate;
}
