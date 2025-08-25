package com.gitbaby.club.controller;

import com.gitbaby.club.domain.dto.NoteDTO;
import com.gitbaby.club.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("note")
@RequiredArgsConstructor
public class NoteController {
  private final NoteService service;


  @PostMapping
  public ResponseEntity<?> register(@RequestBody NoteDTO dto) {
    log.info("==================================register");
    log.info(dto);

    return ResponseEntity.ok(service.register(dto));
  }

  @GetMapping(value = "{num}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> read(@PathVariable Long num) {
    log.info("==================================read");
    log.info(num);
    return ResponseEntity.ok(service.get(num));
  }

  @GetMapping(value = "all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getList(Long mno) {
    log.info("==================================getList");
    log.info(mno);
    return ResponseEntity.ok(service.getAllWithWriter(mno));
  }

  @PutMapping(value = "{num}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> modify(@RequestBody NoteDTO dto) {
    log.info("================================modify");
    log.info(dto);

    service.modify(dto);

    return ResponseEntity.ok(dto.getNum() + " 인스턴스 수정 완료");
  }

  @DeleteMapping("{num}")
  public ResponseEntity<?> delete(@PathVariable Long num) {
    log.info("================================delete");
    log.info(num);

    service.remove(num);
    return ResponseEntity.ok("삭제 완료");
  }
}
