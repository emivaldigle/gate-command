package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.PocService;
import com.visp.gate_command.controller.PocController;
import com.visp.gate_command.domain.dto.PocDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class PocControllerImpl implements PocController {

  private final PocService pocService;

  @Override
  public ResponseEntity<PocDto> create(PocDto pocDto) {
    return ResponseEntity.ok(pocService.create(pocDto));
  }

  @Override
  public ResponseEntity<PocDto> update(PocDto pocDto, UUID id) {
    return pocService
        .update(pocDto)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    pocService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<PocDto>> retrievePocByEntity(UUID entityId) {
    return ResponseEntity.ok(pocService.retrievePocByEntity(entityId));
  }
}
