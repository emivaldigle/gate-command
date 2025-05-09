package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.business.PocService;
import com.visp.gate_command.controller.EntityController;
import com.visp.gate_command.domain.dto.EntityConfigurationDto;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.exception.NotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class EntityControllerImpl implements EntityController {

  private final EntityService entityService;
  private final PocService pocService;

  @Override
  public ResponseEntity<EntityDto> save(EntityDto entityDto) {
    return ResponseEntity.ok(entityService.create(entityDto));
  }

  @Override
  public ResponseEntity<List<EntityDto>> retrieveAll() {
    return ResponseEntity.ok(entityService.getAll());
  }

  @Override
  public ResponseEntity<EntityDto> findById(UUID id) {
    return entityService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new NotFoundException("Entity not found"));
  }

  @Override
  public ResponseEntity<EntityConfigurationDto> findConfigurationsForEntity(UUID id) {
    final var pocs = pocService.retrievePocByEntity(id);
    final var entity = entityService.findById(id);
    if (entity.isEmpty()) {
      throw new NotFoundException("Not found configuration");
    }
    return ResponseEntity.ok(
        EntityConfigurationDto.builder()
            .active(entity.get().isActive())
            .parkingSizeLimit(entity.get().getParkingSizeLimit())
            .visitSizeLimit(entity.get().getVisitSizeLimit())
            .parkingHoursAllowed(entity.get().getParkingHoursAllowed())
            .lastUpdatedAt(entity.get().getLastUpdatedAt())
            .pocList(pocs)
            .build());
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    entityService.delete(id);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<EntityDto> update(EntityDto entityDto, UUID id) {
    entityDto.setId(id);
    return ResponseEntity.ok(
        entityService
            .update(entityDto)
            .orElseThrow(() -> new RuntimeException("Entity not found")));
  }
}
