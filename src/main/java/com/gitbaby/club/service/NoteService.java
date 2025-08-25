package com.gitbaby.club.service;

import com.gitbaby.club.domain.dto.NoteDTO;
import com.gitbaby.club.domain.entity.ClubMember;
import com.gitbaby.club.domain.entity.Note;

import java.util.List;

public interface NoteService {
  Long register(NoteDTO noteDTO);
  NoteDTO get(Long num);
  void modify(NoteDTO noteDTO);
  void remove(Long num);
  List<NoteDTO> getAllWithWriter(Long writerMno);

  default Note toEntity(NoteDTO noteDTO) {
    return Note.builder()
            .num(noteDTO.getNum())
            .title(noteDTO.getTitle())
            .content(noteDTO.getContent())
            .writer(ClubMember.builder().mno(noteDTO.getWriterMno()).build())
            .build();
  }

  default NoteDTO toDTO(Note note) {
    return NoteDTO.builder()
            .num(note.getNum())
            .title(note.getTitle())
            .content(note.getContent())
            .writerMno(note.getWriter().getMno())
            .regDate(note.getRegDate())
            .modDate(note.getModDate())
            .build();
  }
}
