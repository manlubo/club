package com.gitbaby.club.service;

import com.gitbaby.club.domain.dto.NoteDTO;
import com.gitbaby.club.domain.entity.Note;
import com.gitbaby.club.domain.mapper.NoteMapper;
import com.gitbaby.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NoteServiceImpl implements NoteService{
  private final NoteRepository repository;
  private final NoteMapper mapper;

  @Override
  public Long register(NoteDTO noteDTO) {
    Note note = mapper.toEntity(noteDTO);
    repository.save(note);
    return note.getNum();
  }

  @Override
  @PostAuthorize("returnObject.writerMno == authentication.principal.mno")
  public NoteDTO get(Long num) {

    return repository.getWithWriter(num).map(mapper::toDto).orElseThrow(() -> new IllegalArgumentException("해당 인스턴스를 찾을 수 없습니다."));
  }

  @Override
  public void modify(NoteDTO noteDTO) {
    Long num = noteDTO.getNum();
    Optional<Note> result = repository.findById(num);

    if(result.isPresent()) {
      Note note = result.orElseThrow(() -> new IllegalArgumentException("해당 인스턴스를 찾을 수 없습니다."));
      note.setTitle(noteDTO.getTitle());
      note.setContent(noteDTO.getContent());
      repository.save(note);
    }
  }

  @Override
  public void remove(Long num) {
    repository.deleteById(num);
  }

  @Override
  public List<NoteDTO> getAllWithWriter(Long writerMno) {
    return repository.getList(writerMno).stream().map(mapper::toDto).toList();
  }
}
