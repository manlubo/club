package com.gitbaby.club.domain.mapper;

import com.gitbaby.club.domain.dto.NoteDTO;
import com.gitbaby.club.domain.entity.ClubMember;
import com.gitbaby.club.domain.entity.Note;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

  // DTO -> Entity
  @Mapping(target = "writer", source = "writerMno", qualifiedByName = "mnoToWriter")
  Note toEntity(NoteDTO dto);

  // Entity -> DTO
  @Mapping(target = "writerMno", source = "writer.mno")
  NoteDTO toDto(Note entity);

  // 리스트 매핑
  List<NoteDTO> toDtoList(List<Note> entities);

  // writer 생성 헬퍼
  @Named("mnoToWriter")
  default ClubMember mnoToWriter(Long mno) {
    if (mno == null) return null;
    return ClubMember.builder().mno(mno).build();
  }
}
